package com.dh.myembyapp.sync;

import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 配置信封的校验与解密。整套方案里唯一真正承载安全的部分。
 *
 * <p>三层各管一件事，缺一层破一个口子，不能互相顶替：
 * <ul>
 *   <li>AES-256-GCM —— 保密性：镜像站/爬虫/公开库读者看到的只是密文</li>
 *   <li>ECDSA-P256-SHA256 —— 真实性：只有构建机私钥能签发，镜像站换不掉内容</li>
 *   <li>seq 单调 —— 防回滚：旧信封签名也合法，但不许再被采用（重放）</li>
 * </ul>
 *
 * <p>校验顺序不可颠倒：结构 → 验签 → 防回滚 → 解包 DEK → 解正文。
 * 先验签再解密，保证任何解密动作只发生在"已确认来源"的数据上。
 *
 * <p>API 兼容性（minSdk 23）：用 {@link android.util.Base64}（java.util.Base64 要 API 26）；
 * 用 SHA256withECDSA（Ed25519 要 API 33）；GCMParameterSpec 从 API 21 起可用。
 */
public final class Envelope {

    /** 协议命名空间，参与签名输入与 AAD，改动等于换协议 */
    public static final String NS = "chai-config-v1";
    public static final String VER = "A256GCM+ES256";

    private static final int VERSION = 1;
    private static final int TAG_LEN = 16;
    private static final int NONCE_LEN = 12;

    /** 校验失败；code 直接写进 sync_status.txt，便于事后定位是哪一层拒绝的 */
    public static final class Reject extends Exception {
        public final String code;

        Reject(String code, String message) {
            super(message);
            this.code = code;
        }
    }

    /** 校验通过后的结果 */
    public static final class Opened {
        public final JSONObject payload;
        public final long seq;
        public final String kid;
        public final int rawBytes;

        Opened(JSONObject payload, long seq, String kid, int rawBytes) {
            this.payload = payload;
            this.seq = seq;
            this.kid = kid;
            this.rawBytes = rawBytes;
        }
    }

    private final byte[] mPubKey;
    private final byte[] mMaster;

    /**
     * @param pubKeyHex 钉死在 APK 里的签名公钥（SPKI DER 的十六进制，P-256 固定 91 字节）
     * @param masterB64 主密钥（32 字节的 base64），由 build.ps1 从构建参数注入
     */
    public Envelope(String pubKeyHex, String masterB64) {
        if (pubKeyHex == null || pubKeyHex.length() < 64) {
            throw new IllegalArgumentException("pubKeyHex 不合法");
        }
        if (masterB64 == null || masterB64.isEmpty()) {
            throw new IllegalArgumentException("masterB64 不合法");
        }
        mPubKey = hex(pubKeyHex);
        mMaster = Base64.decode(masterB64, Base64.DEFAULT);
        if (mMaster.length != 32) {
            throw new IllegalArgumentException("master 必须是 32 字节，实际 " + mMaster.length);
        }
    }

    /**
     * 验一个信封。
     *
     * @param text    信封 JSON 原文
     * @param lastSeq 已接受过的最大 seq（首次运行传 0）
     */
    public Opened open(String text, long lastSeq) throws Reject {
        JSONObject env;
        try {
            env = new JSONObject(text);
        } catch (Throwable t) {
            // 镜像站返回 HTML 错误页/被截断时会走到这里
            throw new Reject("bad-json", "信封不是合法 JSON");
        }

        // ① 结构与版本
        if (env.optInt("v", 0) != VERSION) {
            throw new Reject("bad-version", "信封版本 " + env.optInt("v", 0) + " 不支持");
        }
        if (!VER.equals(env.optString("alg", ""))) {
            throw new Reject("bad-alg", "算法 " + env.optString("alg", "") + " 不支持");
        }

        final String kid = env.optString("kid", "");
        final long seq = env.optLong("seq", -1);
        final long iat = env.optLong("iat", -1);
        final String n1 = env.optString("n1", "");
        final String wdek = env.optString("wdek", "");
        final String n2 = env.optString("n2", "");
        final String ct = env.optString("ct", "");
        final String sig = env.optString("sig", "");
        if (kid.isEmpty() || seq < 0 || iat < 0
                || n1.isEmpty() || wdek.isEmpty() || n2.isEmpty() || ct.isEmpty() || sig.isEmpty()) {
            throw new Reject("bad-json", "信封字段缺失");
        }

        // ② 验签。签名输入是逐字段换行拼接的规范化字节串，不是 JSON 原文
        //    （键序/空白/转义在两端不一致会让"本地验得过、电视上验不过"变成偶发故障）
        StringBuilder sb = new StringBuilder(512);
        sb.append(NS).append('\n')
                .append(VERSION).append('\n')
                .append(VER).append('\n')
                .append(kid).append('\n')
                .append(seq).append('\n')
                .append(iat).append('\n')
                .append(n1).append('\n')
                .append(wdek).append('\n')
                .append(n2).append('\n')
                .append(ct);

        try {
            PublicKey pk = KeyFactory.getInstance("EC")
                    .generatePublic(new X509EncodedKeySpec(mPubKey));
            Signature s = Signature.getInstance("SHA256withECDSA");
            s.initVerify(pk);
            s.update(sb.toString().getBytes(StandardCharsets.UTF_8));
            if (!s.verify(b64(sig))) {
                throw new Reject("bad-signature", "签名不匹配：不是我们签发的，或内容被改过");
            }
        } catch (Reject r) {
            throw r;
        } catch (Throwable t) {
            throw new Reject("bad-signature", "验签过程异常: " + t.getClass().getSimpleName());
        }

        // ③ 防回滚。签名合法也可能是很久以前签的，被镜像站拿来重放
        if (seq <= lastSeq) {
            throw new Reject("rollback", "seq=" + seq + " <= last=" + lastSeq);
        }

        // ④ 解包 DEK。kid 与 seq 都进 AAD，防止把 A 信封的密文搬进 B 信封
        byte[] kek = hkdf(mMaster, utf8(kid), utf8("chai-config-kek-v1"), 32);
        byte[] dek = gcmOpen(kek, b64(n1), utf8(NS + "|wrap|" + kid + "|" + seq), b64(wdek), "bad-master");

        // ⑤ 解正文
        byte[] plain = gcmOpen(dek, b64(n2), utf8(NS + "|payload|" + kid + "|" + seq), b64(ct), "bad-ciphertext");

        JSONObject payload;
        try {
            payload = new JSONObject(new String(plain, StandardCharsets.UTF_8));
        } catch (Throwable t) {
            throw new Reject("bad-payload", "正文不是合法 JSON");
        }
        if (payload.has("seq") && payload.optLong("seq", -1) != seq) {
            throw new Reject("seq-mismatch", "内外 seq 不一致");
        }
        return new Opened(payload, seq, kid, text.length());
    }

    /* ------------------------------------------------------------------ */

    private static byte[] gcmOpen(byte[] key, byte[] nonce, byte[] aad, byte[] sealed, String code)
            throws Reject {
        if (nonce.length != NONCE_LEN) {
            throw new Reject("bad-json", "nonce 长度异常 " + nonce.length);
        }
        if (sealed.length <= TAG_LEN) {
            throw new Reject(code, "密文长度异常 " + sealed.length);
        }
        try {
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"),
                    new GCMParameterSpec(TAG_LEN * 8, nonce));
            c.updateAAD(aad);
            return c.doFinal(sealed);
        } catch (Throwable t) {
            throw new Reject(code, "AEAD 校验失败: " + t.getClass().getSimpleName());
        }
    }

    /** HKDF-SHA256 (RFC 5869)。Android 没有内置实现，所以手写。 */
    static byte[] hkdf(byte[] ikm, byte[] salt, byte[] info, int len) {
        byte[] prk;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(salt, "HmacSHA256"));
            prk = mac.doFinal(ikm);
        } catch (Throwable t) {
            return new byte[0];
        }

        byte[] out = new byte[len];
        byte[] prev = new byte[0];
        int pos = 0;
        for (int counter = 1; pos < len && counter < 256; counter++) {
            try {
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(new SecretKeySpec(prk, "HmacSHA256"));
                mac.update(prev);
                mac.update(info);
                mac.update((byte) counter);
                prev = mac.doFinal();
            } catch (Throwable t) {
                return new byte[0];
            }
            int n = Math.min(prev.length, len - pos);
            System.arraycopy(prev, 0, out, pos, n);
            pos += n;
        }
        return out;
    }

    static byte[] b64(String s) {
        return Base64.decode(s, Base64.URL_SAFE | Base64.NO_WRAP);
    }

    static byte[] utf8(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    static byte[] hex(String s) {
        int n = s.length() / 2;
        byte[] out = new byte[n];
        for (int i = 0; i < n; i++) {
            out[i] = (byte) Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
        }
        return out;
    }
}
