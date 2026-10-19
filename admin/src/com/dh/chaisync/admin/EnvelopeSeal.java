package com.dh.chaisync.admin;

import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 信封的封装端。与母 APK 的 {@code Envelope.java}（解封端）、
 * {@code tools/publish.mjs}（命令行封装端）必须是同一套字节协议。
 *
 * <p>为什么子 APK 要自己再实现一遍，而不是复用母 APK 的类：
 * 母 APK 那五个文件是被 {@code splice.py} 塞进别人的 dex、借用别人的 {@code Application} 跑起来的，
 * 包里没有 Activity、没有资源、没有 launcher。它不能独立安装。
 * 所以这里只共用<b>协议</b>，不共用<b>代码</b>——协议是字节约定，代码是宿主环境的产物。
 *
 * <p>三处最容易写错、且错了以后现象很难查的地方：
 * <ol>
 *   <li>base64url 必须<b>无 padding</b>。Node 的 {@code toString('base64url')} 不带 {@code =}；
 *       这里若带 padding，签名输入两端就不同 → 电视报 bad-signature。</li>
 *   <li>签名输入是<b>逐字段换行拼接</b>，不是 JSON 原文。键序/空白/转义在两端不一致，
 *       会让"本地验得过、电视上验不过"变成偶发故障。</li>
 *   <li>GCM 的密文尾部 16 字节 tag 必须和密文连在一起编码。Java 的 {@code doFinal}
 *       已经把它拼在末尾，Node 那边是显式 {@code concat([update, final, getAuthTag()])}。</li>
 * </ol>
 */
public final class EnvelopeSeal {

    public static final String NS = "chai-config-v1";
    public static final String VER = "A256GCM+ES256";

    private static final int VERSION = 1;
    private static final int TAG_BITS = 128;
    private static final int NONCE_LEN = 12;

    private EnvelopeSeal() {
    }

    /* ------------------------------------------------------------------ */
    /* 封装                                                                */
    /* ------------------------------------------------------------------ */

    /**
     * 把明文打成信封。
     *
     * @param plaintext 正文 JSON 的 UTF-8 字节
     * @param master    32 字节主密钥（与母 APK 内置的一致）
     * @param kid       密钥标识，参与 HKDF 的 salt 与 AAD
     * @param seq       单调计数，必须大于任何已发布过的值
     * @param priv      签名私钥（PKCS#8，P-256）
     */
    public static JSONObject seal(byte[] plaintext, byte[] master, String kid, long seq,
                                  PrivateKey priv, SecureRandom rnd) throws Exception {
        if (master == null || master.length != 32) {
            throw new IllegalArgumentException("master 必须是 32 字节，实际 "
                    + (master == null ? 0 : master.length));
        }
        if (kid == null || kid.isEmpty()) {
            throw new IllegalArgumentException("kid 不能为空");
        }
        if (seq <= 0) {
            throw new IllegalArgumentException("seq 必须是正整数");
        }

        byte[] kek = hkdf(master, utf8(kid), utf8("chai-config-kek-v1"), 32);
        byte[] dek = new byte[32];
        rnd.nextBytes(dek);
        // nonce 每次发布必须随机。拿 seq 当 nonce 会复用密钥流，明文可被还原。
        byte[] n1 = new byte[NONCE_LEN];
        byte[] n2 = new byte[NONCE_LEN];
        rnd.nextBytes(n1);
        rnd.nextBytes(n2);

        byte[] wdek = gcmSeal(kek, n1, utf8(NS + "|wrap|" + kid + "|" + seq), dek);
        byte[] ct = gcmSeal(dek, n2, utf8(NS + "|payload|" + kid + "|" + seq), plaintext);

        JSONObject e = new JSONObject();
        e.put("v", VERSION);
        e.put("alg", VER);
        e.put("kid", kid);
        e.put("seq", seq);
        e.put("iat", System.currentTimeMillis() / 1000L);
        e.put("n1", b64u(n1));
        e.put("wdek", b64u(wdek));
        e.put("n2", b64u(n2));
        e.put("ct", b64u(ct));
        e.put("sig", b64u(sign(e, priv)));
        return e;
    }

    /** 签名输入：逐字段换行拼接。改这个函数等于换协议，母 APK 会全部验签失败。 */
    private static byte[] signingInput(JSONObject e) throws Exception {
        StringBuilder sb = new StringBuilder(512);
        sb.append(NS).append('\n')
                .append(e.getInt("v")).append('\n')
                .append(e.getString("alg")).append('\n')
                .append(e.getString("kid")).append('\n')
                .append(e.getLong("seq")).append('\n')
                .append(e.getLong("iat")).append('\n')
                .append(e.getString("n1")).append('\n')
                .append(e.getString("wdek")).append('\n')
                .append(e.getString("n2")).append('\n')
                .append(e.getString("ct"));
        return utf8(sb.toString());
    }

    private static byte[] sign(JSONObject e, PrivateKey priv) throws Exception {
        Signature s = Signature.getInstance("SHA256withECDSA");
        s.initSign(priv);
        s.update(signingInput(e));
        return s.sign();
    }

    /* ------------------------------------------------------------------ */
    /* 发布前自检                                                          */
    /* ------------------------------------------------------------------ */

    /**
     * 用公钥验签、再用 master 解密，把正文取回来。
     *
     * <p>这一步不是"多此一举的测试"，而是发布流程里唯一的止损点：
     * 信封一旦推上 GitHub，所有电视会在下一次启动时静默拒绝，而你看不到任何报错。
     * 在本地先解开一遍，能把"kid 打错""master 拷错""私钥不是这把公钥的"这三类
     * 事故挡在推送之前。
     */
    public static byte[] openSelf(JSONObject e, byte[] master, String pubKeyHex) throws Exception {
        if (e.getInt("v") != VERSION) {
            throw new IllegalStateException("自检失败：信封版本不支持");
        }
        if (!VER.equals(e.getString("alg"))) {
            throw new IllegalStateException("自检失败：算法标记不是 " + VER);
        }

        PublicKey pk = KeyFactory.getInstance("EC")
                .generatePublic(new X509EncodedKeySpec(hex(pubKeyHex)));
        Signature s = Signature.getInstance("SHA256withECDSA");
        s.initVerify(pk);
        s.update(signingInput(e));
        if (!s.verify(b64uDecode(e.getString("sig")))) {
            throw new IllegalStateException(
                    "自检失败：签名验不过。多半是 signing.pem 与 pubKeyHex 不是同一对密钥。");
        }

        String kid = e.getString("kid");
        long seq = e.getLong("seq");
        byte[] kek = hkdf(master, utf8(kid), utf8("chai-config-kek-v1"), 32);
        byte[] dek = gcmOpen(kek, b64uDecode(e.getString("n1")),
                utf8(NS + "|wrap|" + kid + "|" + seq), b64uDecode(e.getString("wdek")));
        return gcmOpen(dek, b64uDecode(e.getString("n2")),
                utf8(NS + "|payload|" + kid + "|" + seq), b64uDecode(e.getString("ct")));
    }

    /** 公钥指纹，用于人工核对"手机上的发布端"与"电视上的接收端"是同一对密钥。 */
    public static String fingerprint(String pubKeyHex) {
        try {
            byte[] d = MessageDigest.getInstance("SHA-256").digest(hex(pubKeyHex));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                if (i > 0) {
                    sb.append(':');
                }
                // byte 提升成 int 时负数会带符号位，不 &0xFF 会打出 "FFFFFFA3"
                sb.append(String.format("%02X", d[i] & 0xFF));
            }
            return sb.toString();
        } catch (Throwable t) {
            return "??";
        }
    }

    /* ------------------------------------------------------------------ */
    /* 密钥与编解码                                                        */
    /* ------------------------------------------------------------------ */

    /**
     * 解析 PKCS#8 的 EC 私钥 PEM。
     *
     * <p>只接受 {@code BEGIN PRIVATE KEY}（PKCS#8）。如果拿到的是
     * {@code BEGIN EC PRIVATE KEY}（SEC1），KeyFactory 不会认，这里直接给出可读的错。
     */
    public static PrivateKey parsePrivatePem(String pem) throws Exception {
        if (pem == null || pem.trim().isEmpty()) {
            throw new IllegalArgumentException("signing.pem 为空");
        }
        String t = pem;
        if (t.contains("BEGIN EC PRIVATE KEY")) {
            throw new IllegalArgumentException(
                    "这是 SEC1 格式（BEGIN EC PRIVATE KEY）。请用 tools/publish.mjs genkey 生成的"
                            + " PKCS#8 格式（BEGIN PRIVATE KEY）。");
        }
        t = t.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] der = Base64.decode(t, Base64.DEFAULT);
        try {
            return KeyFactory.getInstance("EC").generatePrivate(new PKCS8EncodedKeySpec(der));
        } catch (Throwable e) {
            throw new IllegalArgumentException("私钥解析失败（需要 P-256 的 PKCS#8 PEM）: " + e.getMessage());
        }
    }

    public static boolean looksLikePem(String s) {
        return s != null && s.contains("BEGIN PRIVATE KEY") && s.contains("END PRIVATE KEY");
    }

    static byte[] gcmSeal(byte[] key, byte[] nonce, byte[] aad, byte[] plain) throws Exception {
        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(TAG_BITS, nonce));
        c.updateAAD(aad);
        return c.doFinal(plain);
    }

    static byte[] gcmOpen(byte[] key, byte[] nonce, byte[] aad, byte[] sealed) throws Exception {
        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(TAG_BITS, nonce));
        c.updateAAD(aad);
        return c.doFinal(sealed);
    }

    /** HKDF-SHA256 (RFC 5869)。Android 没有内置实现，手写。与 Envelope.hkdf / publish.mjs hkdf 同构。 */
    static byte[] hkdf(byte[] ikm, byte[] salt, byte[] info, int len) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(salt, "HmacSHA256"));
        byte[] prk = mac.doFinal(ikm);

        byte[] out = new byte[len];
        byte[] prev = new byte[0];
        int pos = 0;
        for (int counter = 1; pos < len && counter < 256; counter++) {
            Mac m = Mac.getInstance("HmacSHA256");
            m.init(new SecretKeySpec(prk, "HmacSHA256"));
            m.update(prev);
            m.update(info);
            m.update((byte) counter);
            prev = m.doFinal();
            int n = Math.min(prev.length, len - pos);
            System.arraycopy(prev, 0, out, pos, n);
            pos += n;
        }
        return out;
    }

    /** 无 padding 的 base64url —— 与 Node 的 base64url 完全一致 */
    static String b64u(byte[] b) {
        return Base64.encodeToString(b, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);
    }

    static byte[] b64uDecode(String s) {
        return Base64.decode(s, Base64.URL_SAFE | Base64.NO_WRAP);
    }

    static byte[] utf8(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    static byte[] hex(String s) {
        String t = s.trim();
        if (t.length() % 2 != 0) {
            throw new IllegalArgumentException("十六进制长度必须是偶数");
        }
        byte[] out = new byte[t.length() / 2];
        for (int i = 0; i < out.length; i++) {
            out[i] = (byte) Integer.parseInt(t.substring(i * 2, i * 2 + 2), 16);
        }
        return out;
    }
}
