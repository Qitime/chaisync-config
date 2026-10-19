package com.dh.chaisync.admin;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Emby 服务端的两个只读/登录接口。
 *
 * <p>为什么子 APK 要自己登录一次，而不是让电视去登录：
 * 母 APK 里那套同步只负责"把已有的 token 写进 prefs"，它不做登录。
 * token 过期后电视会静默地一直用旧 token 失败，用户只能手动再登录一次。
 * 所以刷新令牌必须发生在这台手机上——这是子 APK 存在的主要理由之一。
 *
 * <p>接口约定（Emby 与 Jellyfin 兼容）：
 * <pre>
 *   GET  /System/Info/Public          无需认证，返回 ServerName，用于"测试连通"
 *   POST /Users/AuthenticateByName    body {"Username":"..","Pw":".."}
 *        → {"AccessToken":"..","User":{"Id":".."}}
 * </pre>
 *
 * <p>{@code X-Emby-Authorization} 头不能省：有的服务端没有它直接 400，
 * 而且 DeviceId 会进入服务端的设备列表，填一个稳定的值才不会每次刷新都多出一台"新设备"。
 */
public final class EmbyClient {

    public static final class Auth {
        public String token = "";
        public String userId = "";
        public String userName = "";
    }

    private EmbyClient() {
    }

    /** 由服务器条目拼出 base URL，容忍 path 有/无前导斜杠、port 为 0。 */
    public static String baseOf(JSONObject s) {
        String proto = s.optString("protocol", "http").trim();
        if (!"https".equalsIgnoreCase(proto)) {
            proto = "http";
        }
        String host = s.optString("host", "").trim();
        int port = s.optInt("port", 0);
        String path = s.optString("path", "").trim();

        StringBuilder b = new StringBuilder(proto).append("://").append(host);
        if (port > 0) {
            b.append(':').append(port);
        }
        if (!path.isEmpty() && !"/".equals(path)) {
            if (!path.startsWith("/")) {
                b.append('/');
            }
            b.append(path);
            if (path.endsWith("/")) {
                b.setLength(b.length() - 1);
            }
        }
        return b.toString();
    }

    public static String displayUrl(JSONObject s) {
        String u = baseOf(s);
        return u.replace("https://", "").replace("http://", "");
    }

    /** 连通性测试：只读公开信息，不需要凭据，也不会在服务端留下登录记录。 */
    public static String probe(JSONObject s, int timeoutMs) throws Exception {
        String url = baseOf(s) + "/System/Info/Public";
        HttpURLConnection conn = open(url, "GET", timeoutMs);
        int code = conn.getResponseCode();
        String body = readAll(code >= 400 ? conn.getErrorStream() : conn.getInputStream());
        conn.disconnect();
        if (code < 200 || code >= 300) {
            throw new IllegalStateException("HTTP " + code + (body.isEmpty() ? "" : " " + trunc(body)));
        }
        JSONObject j = new JSONObject(body);
        String name = j.optString("ServerName", "");
        String ver = j.optString("Version", "");
        if (name.isEmpty() && ver.isEmpty()) {
            throw new IllegalStateException("返回的不是 Emby 服务端");
        }
        return (name.isEmpty() ? "Emby" : name) + (ver.isEmpty() ? "" : " · " + ver);
    }

    public static Auth authenticate(JSONObject s, int timeoutMs) throws Exception {
        String user = s.optString("username", "").trim();
        String pw = s.optString("password", "");
        if (user.isEmpty()) {
            throw new IllegalArgumentException("没有填用户名");
        }
        String deviceId = s.optString("deviceId", "");
        if (deviceId.isEmpty() || "null".equals(deviceId)) {
            deviceId = s.optString("id", "chaisync-admin");
        }

        String url = baseOf(s) + "/Users/AuthenticateByName";
        HttpURLConnection conn = open(url, "POST", timeoutMs);
        String auth = "MediaBrowser Client=\"ChaiSync\", Device=\"admin\", DeviceId=\""
                + deviceId + "\", Version=\"1.0\"";
        conn.setRequestProperty("X-Emby-Authorization", auth);
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        JSONObject req = new JSONObject();
        req.put("Username", user);
        req.put("Pw", pw);
        byte[] payload = req.toString().getBytes(StandardCharsets.UTF_8);
        conn.setFixedLengthStreamingMode(payload.length);
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        try {
            os.write(payload);
        } finally {
            os.close();
        }

        int code = conn.getResponseCode();
        String body = readAll(code >= 400 ? conn.getErrorStream() : conn.getInputStream());
        conn.disconnect();

        if (code == 401 || code == 403) {
            throw new IllegalStateException("用户名或密码不对（HTTP " + code + "）");
        }
        if (code < 200 || code >= 300) {
            throw new IllegalStateException("HTTP " + code + (body.isEmpty() ? "" : " " + trunc(body)));
        }

        JSONObject j = new JSONObject(body);
        Auth a = new Auth();
        a.token = j.optString("AccessToken", j.optString("accessToken", ""));
        JSONObject u = j.optJSONObject("User");
        a.userId = u != null ? u.optString("Id", "") : j.optString("UserId", "");
        a.userName = u != null ? u.optString("Name", "") : "";
        if (a.token.isEmpty() || a.userId.isEmpty()) {
            throw new IllegalStateException("响应里没有 AccessToken / UserId");
        }
        return a;
    }

    /* ------------------------------------------------------------------ */

    private static HttpURLConnection open(String url, String method, int timeoutMs) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod(method);
        conn.setConnectTimeout(Math.min(timeoutMs, 8000));
        conn.setReadTimeout(timeoutMs);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "ChaiSync-Admin/1.0");
        return conn;
    }

    private static String readAll(InputStream in) throws Exception {
        if (in == null) {
            return "";
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] buf = new byte[2048];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
            return out.toString("UTF-8");
        } finally {
            in.close();
        }
    }

    private static String trunc(String s) {
        String t = s.replaceAll("\\s+", " ").trim();
        return t.length() > 120 ? t.substring(0, 120) + "…" : t;
    }
}
