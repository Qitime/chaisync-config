package android.net;

/** 交叉验证用的垫片。只实现 Publisher/GitHubClient 用到的一个方法。 */
public final class Uri {

    private Uri() {
    }

    public static String encode(String s) {
        try {
            return java.net.URLEncoder.encode(s, "UTF-8").replace("+", "%20");
        } catch (Throwable t) {
            return s;
        }
    }
}
