package com.dh.chaisync.admin;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * 本地保险库：把整个管理状态（含发布私钥、PAT、Emby 口令）作为<b>一个</b>密文文件存取。
 *
 * <p>为什么整包加密而不是逐字段：
 * <ul>
 *   <li>逐字段加密要为每个字段存一个 nonce，代码多、容易漏；</li>
 *   <li>整体加密天然保证"要么全在，要么全废"，不会出现半新半旧的状态；</li>
 *   <li>数据量只有几 KB，每次整体重写没有成本。</li>
 * </ul>
 *
 * <p>密钥来自 AndroidKeyStore：不可导出、绑定设备、不受 adb 备份影响。
 * 这意味着<b>换手机不能靠备份迁移</b>，只能在新手机上重新粘贴一次 bootstrap JSON。
 * 这是有意的取舍——能备份的私钥等于能复制的私钥。
 *
 * <p>文件格式：{@code "CSV1" || iv(12) || ciphertext||tag}
 */
public final class Vault {

    private static final String KEY_ALIAS = "chaisync-admin-vault-v1";
    private static final String FILE_NAME = "vault.bin";
    private static final byte[] MAGIC = {'C', 'S', 'V', '1'};
    private static final int NONCE_LEN = 12;
    private static final int TAG_BITS = 128;

    private Vault() {
    }

    public static boolean exists(Context ctx) {
        return file(ctx).isFile();
    }

    /** 读状态。文件不存在返回空骨架；存在但解不开抛异常（说明存储被破坏或来自别的设备）。 */
    public static JSONObject load(Context ctx) throws Exception {
        File f = file(ctx);
        if (!f.isFile()) {
            return emptyState();
        }
        byte[] all = readAll(f);
        if (all.length < MAGIC.length + NONCE_LEN + 17) {
            throw new IllegalStateException("保险库文件长度异常，可能被截断");
        }
        for (int i = 0; i < MAGIC.length; i++) {
            if (all[i] != MAGIC[i]) {
                throw new IllegalStateException("保险库文件头不对");
            }
        }
        byte[] iv = new byte[NONCE_LEN];
        System.arraycopy(all, MAGIC.length, iv, 0, NONCE_LEN);
        byte[] body = new byte[all.length - MAGIC.length - NONCE_LEN];
        System.arraycopy(all, MAGIC.length + NONCE_LEN, body, 0, body.length);

        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        c.init(Cipher.DECRYPT_MODE, key(), new GCMParameterSpec(TAG_BITS, iv));
        byte[] plain = c.doFinal(body);
        return new JSONObject(new String(plain, "UTF-8"));
    }

    public static void save(Context ctx, JSONObject state) throws Exception {
        byte[] plain = state.toString().getBytes("UTF-8");

        // 这里不能自己造 IV。
        // AndroidKeyStore 生成的 AES 密钥默认 setRandomizedEncryptionRequired(true)，
        // 此时 init(ENCRYPT_MODE, key, new GCMParameterSpec(...)) 会抛
        // InvalidKeyException("caller-provided iv not permitted")。
        // 正确做法：不传 IV，让 Keystore 生成，加密后用 getIV() 取回它实际用的那个。
        // 解密方向不受此限制，仍然按存下来的 IV 走。
        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        c.init(Cipher.ENCRYPT_MODE, key());
        byte[] iv = c.getIV();
        if (iv == null || iv.length != NONCE_LEN) {
            throw new IllegalStateException("Keystore 给出的 IV 长度异常："
                    + (iv == null ? "null" : iv.length + " 字节"));
        }
        byte[] sealed = c.doFinal(plain);

        ByteArrayOutputStream out = new ByteArrayOutputStream(sealed.length + 32);
        out.write(MAGIC);
        out.write(iv);
        out.write(sealed);

        // 先写临时文件再 rename：中途断电不会留下半个保险库
        File tmp = new File(ctx.getFilesDir(), FILE_NAME + ".tmp");
        FileOutputStream fos = new FileOutputStream(tmp);
        try {
            fos.write(out.toByteArray());
            fos.getFD().sync();
        } finally {
            fos.close();
        }
        if (!tmp.renameTo(file(ctx))) {
            throw new IllegalStateException("保险库写盘失败（rename）");
        }
    }

    /** 清空。连 Keystore 里的密钥一起删，旧密文从此不可恢复。 */
    public static void wipe(Context ctx) {
        file(ctx).delete();
        new File(ctx.getFilesDir(), FILE_NAME + ".tmp").delete();
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            if (ks.containsAlias(KEY_ALIAS)) {
                ks.deleteEntry(KEY_ALIAS);
            }
        } catch (Throwable ignored) {
        }
    }

    /* ------------------------------------------------------------------ */

    public static JSONObject emptyState() {
        JSONObject s = new JSONObject();
        try {
            s.put("v", 1);
            s.put("localSeq", 0);
            s.put("servers", new JSONArray());
        } catch (Throwable ignored) {
        }
        return s;
    }

    public static boolean isReady(JSONObject state) {
        JSONObject b = state == null ? null : state.optJSONObject("bootstrap");
        if (b == null) {
            return false;
        }
        return !b.optString("repo", "").isEmpty()
                && !b.optString("token", "").isEmpty()
                && !b.optString("masterB64", "").isEmpty()
                && EnvelopeSeal.looksLikePem(b.optString("signingPem", ""))
                && !b.optString("pubKeyHex", "").isEmpty();
    }

    /* ------------------------------------------------------------------ */

    private static SecretKey key() throws Exception {
        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        ks.load(null);
        if (ks.containsAlias(KEY_ALIAS)) {
            return (SecretKey) ks.getKey(KEY_ALIAS, null);
        }
        KeyGenerator kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        kg.init(new KeyGenParameterSpec.Builder(KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .setUserAuthenticationRequired(false)
                // 保持 true（Android 默认值，这里显式写出来防回退）：
                // IV 必须由 Keystore 生成，调用方不得指定。所以 save() 里只能
                // init(ENCRYPT_MODE, key) 两参数，再用 getIV() 取回实际用的 IV。
                .setRandomizedEncryptionRequired(true)
                .build());
        return (SecretKey) kg.generateKey();
    }

    private static File file(Context ctx) {
        return new File(ctx.getFilesDir(), FILE_NAME);
    }

    private static byte[] readAll(File f) throws Exception {
        InputStream in = new FileInputStream(f);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream((int) Math.max(64, f.length()));
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
            return out.toByteArray();
        } finally {
            in.close();
        }
    }

    /* 供 UI 显示用：不暴露任何密钥内容 */
    public static String describe(JSONObject state) {
        JSONObject b = state.optJSONObject("bootstrap");
        if (b == null) {
            return "未初始化";
        }
        return b.optString("repo", "?") + " · seq " + state.optLong("localSeq", 0);
    }

    static String b64(byte[] b) {
        return Base64.encodeToString(b, Base64.NO_WRAP);
    }
}
