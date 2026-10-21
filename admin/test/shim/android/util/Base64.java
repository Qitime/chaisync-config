package android.util;

/** 宿主 JVM 测试用的 android.util.Base64 替身（DEFAULT / URL_SAFE / NO_WRAP / NO_PADDING）。 */
public final class Base64 {

    public static final int DEFAULT = 0;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int CRLF = 4;
    public static final int URL_SAFE = 8;

    private Base64() {
    }

    public static String encodeToString(byte[] input, int flags) {
        java.util.Base64.Encoder enc = (flags & URL_SAFE) != 0
                ? java.util.Base64.getUrlEncoder()
                : java.util.Base64.getEncoder();
        if ((flags & NO_PADDING) != 0) {
            enc = enc.withoutPadding();
        }
        return enc.encodeToString(input);
    }

    public static byte[] decode(String str, int flags) {
        return (flags & URL_SAFE) != 0
                ? java.util.Base64.getUrlDecoder().decode(str)
                : java.util.Base64.getDecoder().decode(str);
    }

    public static byte[] encode(byte[] input, int flags) {
        return encodeToString(input, flags).getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }
}
