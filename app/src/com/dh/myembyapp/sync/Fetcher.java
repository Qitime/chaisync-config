package com.dh.myembyapp.sync;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 单个 URL 的抓取，带硬超时。
 *
 * <p>为什么要开线程再 join，而不是直接 conn.getResponseCode()：
 * ContentProvider.onCreate 跑在主线程，而 setConnectTimeout/setReadTimeout 只管 TCP 与 socket 读，
 * 不管 DNS 解析、TLS 握手、以及镜像站那种"连上了但一个字节一个字节往外挤"的行为。
 * 开线程 + join 是唯一能保证"到点就返回"的做法——首屏时间不能被一个坏镜像拖死。
 */
public final class Fetcher {

    private static final String TAG = "ChaiSync";

    /** 部分镜像会拒绝空 UA 或 Java 默认 UA，用常见形态避免被当爬虫 */
    private static final String UA =
            "Mozilla/5.0 (Linux; Android 9; TV) AppleWebKit/537.36 (KHTML, like Gecko) ChaiSync/1";

    public static final class Result {
        public final int status;
        public final String body;
        public final String detail;
        public final long ms;

        Result(int status, String body, String detail, long ms) {
            this.status = status;
            this.body = body;
            this.detail = detail;
            this.ms = ms;
        }

        public boolean usable() {
            return status == 200 && body != null && body.length() > 0;
        }
    }

    private Fetcher() {
    }

    /**
     * 拼一个镜像地址。mirror 形如 https://ghfast.top/ ，rawUrl 形如
     * https://raw.githubusercontent.com/o/r/main/dist/config.enc.json
     *
     * <p>{@code ?v=} 是缓存键，不是版本校验（版本靠信封里的 seq）。必须带，因为 raw 有
     * {@code Cache-Control: max-age=300}（实测），不带的话发布后最多 5 分钟仍拿到旧信封。
     * Fastly 的 cache key 含 query，所以换一个 v 就等于换一个缓存条目。
     *
     * @param buster 正常传已知 seq；怀疑拿到陈旧缓存时传 {@code "t" + System.currentTimeMillis()}
     */
    public static String buildUrl(String mirror, String rawUrl, String buster) {
        String m = mirror.endsWith("/") ? mirror : mirror + "/";
        return m + rawUrl + "?v=" + buster;
    }

    public static Result get(final String url, final int timeoutMs) {
        Fetch f = start(url, timeoutMs);
        f.join(timeoutMs + 200L);
        Result r = f.poll();
        if (r != null) {
            return r;
        }
        return new Result(0, null, "timeout>" + timeoutMs + "ms", System.currentTimeMillis() - f.started);
    }

    /* ------------------------------------------------------------------ */
    /* 异步句柄：镜像竞速用。start 发出不等待，poll 非阻塞收结果          */
    /* ------------------------------------------------------------------ */

    /** 一次已发出、尚未（或已经）完成的抓取。线程安全：holder 是 volatile 语义数组。 */
    public static final class Fetch {
        private final Result[] holder = new Result[1];
        private final Thread thread;
        private final long started = System.currentTimeMillis();
        /** 结果已被上层消费过（避免竞速轮询里重复验签同一个结果） */
        public boolean taken;

        private Fetch(final String url, final int timeoutMs) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    holder[0] = fetchOnce(url, timeoutMs);
                }
            }, "chai-fetch");
        }

        /** 非阻塞：完成返回结果，未完成返回 null。 */
        public Result poll() {
            return holder[0];
        }

        /** 阻塞等待最多 ms。 */
        public void join(long ms) {
            if (ms > 0) {
                try {
                    thread.join(ms);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        boolean done() {
            return holder[0] != null || !thread.isAlive();
        }
    }

    /** 发出一个抓取，立即返回。线程为守护线程，没人 join 也会随进程退出。 */
    public static Fetch start(final String url, final int timeoutMs) {
        Fetch f = new Fetch(url, timeoutMs);
        f.thread.setDaemon(true);
        f.thread.start();
        return f;
    }

    /** 同步抓一次（get/start 的公共实现）。 */
    private static Result fetchOnce(String url, int timeoutMs) {
        final long started = System.currentTimeMillis();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(timeoutMs);
            conn.setReadTimeout(timeoutMs);
            conn.setRequestProperty("User-Agent", UA);
            conn.setRequestProperty("Accept", "application/json, text/plain, */*");
            // 不要跟随到不可预期的跨主机跳转；镜像站正常情况下是 200 直出
            conn.setInstanceFollowRedirects(true);

            int code = conn.getResponseCode();
            if (code != 200) {
                String err = readSmall(conn.getErrorStream());
                return new Result(code, null,
                        "http " + code + (err.isEmpty() ? "" : (" " + err)),
                        System.currentTimeMillis() - started);
            }
            return new Result(200, readAll(conn.getInputStream()), "",
                    System.currentTimeMillis() - started);
        } catch (Throwable e) {
            return new Result(0, null, e.getClass().getSimpleName(),
                    System.currentTimeMillis() - started);
        } finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Throwable ignored) {
                }
            }
        }
    }

    private static String readAll(InputStream in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(8192);
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
                // 信封只有几 KB；超过 1MB 一定是拿到了别的东西（登录页/大文件），及时掐断
                if (out.size() > 1024 * 1024) {
                    break;
                }
            }
            in.close();
            return out.toString("UTF-8");
        } catch (Throwable t) {
            return "";
        }
    }

    private static String readSmall(InputStream in) {
        if (in == null) {
            return "";
        }
        try {
            byte[] buf = new byte[256];
            int n = in.read(buf);
            in.close();
            if (n <= 0) {
                return "";
            }
            return new String(buf, 0, n, "UTF-8").replace('\n', ' ').trim();
        } catch (Throwable t) {
            return "";
        }
    }
}
