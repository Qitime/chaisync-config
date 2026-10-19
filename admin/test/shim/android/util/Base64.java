package android.util;

/**
 * 交叉验证用的垫片，不是 APK 里的代码。
 *
 * <p>作用：让 {@code EnvelopeSeal.java}（子 APK）与 {@code Envelope.java}（母 APK）
 * 这两份<b>真实源码</b>能在桌面 JVM 上编译运行。Android 的 Base64 与 JDK 的语义差异
 * （padding、URL 字母表、换行）是真实的互通风险点，用垫片复现它，才能测出真问题。
 */
public final class Base64 {

    public static final int DEFAULT = 0;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    private Base64() {
    }

    public static String encodeToString(byte[] input, int flags) {
        java.util.Base64.Encoder e = (flags & URL_SAFE) != 0
                ? java.util.Base64.getUrlEncoder()
                : java.util.Base64.getEncoder();
        if ((flags & NO_PADDING) != 0) {
            e = e.withoutPadding();
        }
        return e.encodeToString(input);
    }

    public static byte[] decode(String str, int flags) {
        if (str == null) {
            return new byte[0];
        }
        String t = str.trim();
        if ((flags & URL_SAFE) != 0) {
            java.util.Base64.Decoder d = java.util.Base64.getUrlDecoder();
            try {
                return d.decode(t);
            } catch (IllegalArgumentException e) {
                // 无 padding 的末尾长度不是 4 的倍数时，部分 JDK 实现要求补齐
                return d.decode(pad(t));
            }
        }
        // Android 的 DEFAULT 容忍换行与空白，对应 MIME 解码器
        return java.util.Base64.getMimeDecoder().decode(t);
    }

    private static String pad(String s) {
        int r = s.length() % 4;
        if (r == 0) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < 4 - r; i++) {
            sb.append('=');
        }
        return sb.toString();
    }
}
