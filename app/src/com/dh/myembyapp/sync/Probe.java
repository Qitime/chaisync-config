package com.dh.myembyapp.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import java.net.HttpURLConnection;
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
        return checkServer(srv, tcpMs, httpMs);
    }

    /**
     * 对指定服务器做健康判断。
     *
     * <p>只有一次 HTTP 请求（GET /Users/Me），不再先发一个裸 TCP 探测：
     * HTTP 的连接阶段本身就携带 TCP 的全部信息（Connection refused / UnknownHost /
     * connect timeout），单独的 TCP 段是每次冷启动白付的一个往返。
     * 原 TCP 预算（tcpMs）现在用作连接超时，httpMs 用作读超时，总上限不变。
     *
     * <p>失败原因与旧版一一对应（sync_status.txt 语义不变）：
     * <pre>
     *   200                       → ok
     *   401/403                   → token-invalid:N   （跑 refresh 重新发布）
     *   其他 4xx/5xx              → ok(http N)        （HTTP 活着，不算坏配置）
     *   Connection refused / 未知主机 / connect 超时 → tcp-fail:*  （触发修复拉取）
     *   read 超时                 → ok(http-slow)     （端口活着只是慢，别修复）
     *   TLS 异常                  → tls-fail:*        （触发修复拉取）
     * </pre>
     */
    public static String checkServer(JSONObject srv, int tcpMs, int httpMs) {
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

        String base = protocol + "://" + host + ":" + port
                + (path.isEmpty() ? "" : "/" + path);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(base + "/Users/Me").openConnection();
            conn.setConnectTimeout(Math.max(tcpMs, 300));
            conn.setReadTimeout(Math.max(httpMs, 300));
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
        } catch (java.net.ConnectException e) {
            // 端口拒绝：服务器没活 / 端口被封
            return "tcp-fail:ConnectException";
        } catch (java.net.UnknownHostException e) {
            // 域名没了：服务器到期 / 换地址
            return "tcp-fail:UnknownHost";
        } catch (SocketTimeoutException e) {
            // 区分连接超时（不可达，修复）与读超时（活着只是慢，别修复）
            String msg = String.valueOf(e.getMessage()).toLowerCase(java.util.Locale.US);
            if (msg.contains("connect")) {
                return "tcp-fail:timeout";
            }
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

    /**
     * 当前服务器不可用时，在其余服务器中找一台能用的。
     *
     * <p>所有候选<b>并行</b>探测（每台独立拿全额 tcpMs + httpMs），按 server_order
     * 优先级轮询收结果、第一个健康的即选中。旧版逐台串行时最坏把 3.5s 预算花在
     * 前面的死服务器上；并行后总耗时 ≈ 最快那台健康服务器的响应时间，
     * 全部失败也就 2.7s 左右（单台探测上限），不会拖满预算。
     *
     * <p>只在启动阶段调用。成功即写 last_used_server，之后 MainActivity.onCreate
     * 会直接读到新服务器。
     *
     * @return 切换到的服务器 id；没有可用服务器时返回 null
     */
    public static String failover(Context ctx, int tcpMs, int httpMs, long budgetMs) {
        try {
            SharedPreferences sp = ctx.getSharedPreferences(SERVER_PREFS, 0);
            JSONArray servers = orderedServers(ctx);
            if (servers.length() <= 1) {
                return null;
            }
            String currentId = sp.getString("last_used_server", "");

            // 候选 = 排除当前服务器，保持 server_order 优先级
            java.util.List<JSONObject> candidates = new java.util.ArrayList<JSONObject>();
            for (int i = 0; i < servers.length(); i++) {
                JSONObject o = servers.optJSONObject(i);
                if (o != null && !currentId.equals(o.optString("id", ""))) {
                    candidates.add(o);
                }
            }
            int n = candidates.size();
            if (n == 0) {
                return null;
            }

            // 并行探测：守护线程各自 checkServer，结果写进 results[i]
            final JSONObject[] objs = candidates.toArray(new JSONObject[0]);
            final String[] results = new String[n];
            final String[] labels = new String[n];
            for (int i = 0; i < n; i++) {
                labels[i] = objs[i].optString("alias", objs[i].optString("host",
                        objs[i].optString("id", "")));
            }
            for (int i = 0; i < n; i++) {
                final int idx = i;
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        results[idx] = checkServer(objs[idx], tcpMs, httpMs);
                    }
                }, "chai-failover-" + i);
                t.setDaemon(true);
                t.start();
            }

            // 按优先级轮询：同一窗口内多个完成时取 order 最靠前的健康者
            long deadline = System.currentTimeMillis() + Math.max(budgetMs, 1000L);
            boolean[] decided = new boolean[n];
            int pending = n;
            String winner = null;
            while (pending > 0 && winner == null) {
                for (int i = 0; i < n && winner == null; i++) {
                    if (decided[i] || results[i] == null) {
                        continue;
                    }
                    decided[i] = true;
                    pending--;
                    if (results[i].startsWith("ok")) {
                        winner = objs[i].optString("id", "");
                        Probe.journal(ctx, "failover -> " + labels[i] + " (" + results[i] + ")");
                    } else {
                        Probe.journal(ctx, "failover try " + labels[i] + " -> " + results[i]);
                    }
                }
                if (winner == null && pending > 0) {
                    if (System.currentTimeMillis() >= deadline) {
                        Probe.journal(ctx, "failover budget-exhausted");
                        break;
                    }
                    Thread.sleep(80);
                }
            }

            if (winner != null) {
                sp.edit().putString("last_used_server", winner).commit();
                return winner;
            }
            Probe.journal(ctx, "failover none available");
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } catch (Throwable t) {
            Probe.journal(ctx, "failover crashed: " + t.getClass().getSimpleName());
            return null;
        }
    }

    /** 按 server_order 排序；order 里没有的条目追加到末尾。 */
    private static JSONArray orderedServers(Context ctx) {
        JSONArray servers;
        try {
            servers = new JSONArray(ctx.getSharedPreferences(SERVER_PREFS, 0)
                    .getString("servers", "[]"));
        } catch (Throwable t) {
            return new JSONArray();
        }
        JSONArray order;
        try {
            order = new JSONArray(ctx.getSharedPreferences(SERVER_PREFS, 0)
                    .getString("server_order", "[]"));
        } catch (Throwable t) {
            order = new JSONArray();
        }
        JSONArray out = new JSONArray();
        java.util.Set<String> used = new java.util.HashSet<String>();
        for (int i = 0; i < order.length(); i++) {
            String id = order.optString(i, "");
            for (int j = 0; j < servers.length(); j++) {
                JSONObject o = servers.optJSONObject(j);
                if (o != null && id.equals(o.optString("id", ""))) {
                    out.put(o);
                    used.add(id);
                    break;
                }
            }
        }
        for (int j = 0; j < servers.length(); j++) {
            JSONObject o = servers.optJSONObject(j);
            if (o != null && !used.contains(o.optString("id", ""))) {
                out.put(o);
            }
        }
        return out;
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
