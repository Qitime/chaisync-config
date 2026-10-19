package com.dh.myembyapp.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.SSLException;

/**
 * 判断"电视上当前这套服务器配置还能不能用"。
 *
 * <p>这是整套方案里省流量的关键：探测通过就完全不碰网络，GitHub 只在你换了服务器、
 * 或者 token 失效时才会被访问。镜像偶发不稳对日常开机没有任何影响。
 *
 * <p>两道判断，各覆盖一类故障：
 * <pre>
 *   TCP 通          → 地址/端口活着（覆盖：域名换了、服务器到期、端口被封）
 *   /Users/Me 200   → 令牌有效      （覆盖：服务器还在，但账号被停/被踢/token 过期）
 * </pre>
 */
public final class Probe {

    private static final String SERVER_PREFS = "server_configs";

    private Probe() {
    }

    /** 返回以 "ok" 开头表示健康；其余为失败原因 */
    public static String check(Context ctx, int tcpMs, int httpMs) {
        JSONObject srv = currentServer(ctx);
        if (srv == null) {
            return "no-server";
        }
        String token = srv.optString("accessToken", "");
        String userId = srv.optString("userId", "");
        if (token.isEmpty() || userId.isEmpty()) {
            return "not-logged-in";
        }

        String[] ep = endpointOf(srv);
        String protocol = ep[0];
        String host = ep[1];
        int port;
        try {
            port = Integer.parseInt(ep[2]);
        } catch (Throwable t) {
            port = "https".equals(protocol) ? 443 : 8096;
        }
        String path = ep[3];
        if (host.isEmpty()) {
            return "empty-host";
        }

        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port), tcpMs);
        } catch (Throwable t) {
            return "tcp-fail:" + t.getClass().getSimpleName();
        } finally {
            try {
                socket.close();
            } catch (Throwable ignored) {
            }
        }

        if (httpMs <= 0) {
            return "ok(tcp-only)";
        }

        String base = protocol + "://" + host + ":" + port
                + (path.isEmpty() ? "" : "/" + path);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(base + "/Users/Me").openConnection();
            conn.setConnectTimeout(httpMs);
            conn.setReadTimeout(httpMs);
            conn.setRequestProperty("X-Emby-Token", token);
            conn.setRequestProperty("Accept", "application/json");
            int code = conn.getResponseCode();
            if (code == 200) {
                return "ok";
            }
            if (code == 401 || code == 403) {
                return "token-invalid:" + code;
            }
            return "ok(http " + code + ")";
        } catch (SocketTimeoutException e) {
            // 端口活着，只是慢；不算坏配置，重拉一次反而更糟
            return "ok(http-slow)";
        } catch (SSLException e) {
            return "tls-fail:" + e.getClass().getSimpleName();
        } catch (Throwable t) {
            return "http-fail:" + t.getClass().getSimpleName();
        } finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Throwable ignored) {
                }
            }
        }
    }

    /** 当前生效服务器：last_used_server 指向的那条，找不到就取第一条 */
    public static JSONObject currentServer(Context ctx) {
        try {
            SharedPreferences sp = ctx.getSharedPreferences(SERVER_PREFS, 0);
            JSONArray servers = new JSONArray(sp.getString("servers", "[]"));
            String lastId = sp.getString("last_used_server", "");
            JSONObject fallback = null;
            for (int i = 0; i < servers.length(); i++) {
                JSONObject o = servers.optJSONObject(i);
                if (o == null) {
                    continue;
                }
                if (fallback == null) {
                    fallback = o;
                }
                if (!lastId.isEmpty() && lastId.equals(o.optString("id"))) {
                    return o;
                }
            }
            return fallback;
        } catch (Throwable t) {
            return null;
        }
    }

    /** 解析出真正生效的 {protocol, host, port, path}，备用线路优先 */
    public static String[] endpointOf(JSONObject srv) {
        String protocol = srv.optString("protocol", "http");
        String host = srv.optString("host", "");
        int port = srv.optInt("port", 8096);
        String path = srv.optString("path", "");

        String activeId = srv.optString("activeBackupRouteId", "");
        if (!activeId.isEmpty()) {
            JSONArray routes = srv.optJSONArray("backupRoutes");
            if (routes != null) {
                for (int i = 0; i < routes.length(); i++) {
                    JSONObject r = routes.optJSONObject(i);
                    if (r == null || !activeId.equals(r.optString("id"))) {
                        continue;
                    }
                    protocol = r.optString("protocol", protocol);
                    host = r.optString("host", host);
                    port = r.optInt("port", port);
                    path = r.optString("path", path);
                    break;
                }
            }
        }
        return new String[]{protocol, host, String.valueOf(port), path};
    }

    /** 状态落盘：电视上没有可用 logcat，只能靠这个文件排查（追加写，保留历史） */
    static void journal(Context ctx, String message) {
        Log.i("ChaiSync", message);
        File dir = ctx.getExternalFilesDir(null);
        if (dir == null) {
            return;
        }
        FileOutputStream out = null;
        try {
            if (!dir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();
            }
            File f = new File(dir, "sync_status.txt");
            out = new FileOutputStream(f, true);
            String line = new java.text.SimpleDateFormat("MM-dd HH:mm:ss", java.util.Locale.US)
                    .format(new java.util.Date()) + "  " + message + "\n";
            out.write(line.getBytes(StandardCharsets.UTF_8));
        } catch (Throwable ignored) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Throwable ignored) {
                }
            }
        }
    }
}
