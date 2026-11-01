package com.dh.myembyapp.sync;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Token 失效自愈（A 路线：启动自愈）。
 *
 * <p>ChaiSync / 母壳只在启动时复用本地 token，token 失效后没有任何自动恢复路径，
 * 导致“账密没变却进不了账号”。这里在冷启动探测发现 401/403 时，立刻用本地保存的
 * 账密重登（POST Users/AuthenticateByName），成功后把新 token、userId、isVerified
 * 写回 server_configs prefs —— 同一次启动 MainActivity.onCreate 就会读到新 token。
 *
 * <p>编译边界：本模块独立编译进 classes6.dex，不能引用母壳类，所以直接操作
 * {@code server_configs} 的 JSON（与 {@link Store} 相同的方式）。
 */
public final class SelfHeal {

    private static final String SERVER_PREFS = "server_configs";

    /** 本次启动只弹一次，防止多个失败路径重复触发 */
    private static volatile boolean sNotifiedBoot = false;

    private SelfHeal() {
    }

    /**
     * 尝试用本地账密重登当前服务器。
     *
     * <p>流程：读当前服务器 → 探测 /Users/Me → 仅当结果以 token-invalid 开头才重登
     * → 成功则写回 prefs。
     *
     * @return 重登并写回成功返回 true；无账密 / token 仍有效 / 网络或服务端拒绝返回 false
     */
    public static boolean tryRelogin(Context ctx, int tcpMs, int httpMs) {
        try {
            JSONObject srv = Probe.currentServer(ctx);
            if (srv == null) {
                return false;
            }
            String username = srv.optString("username", "");
            String password = srv.optString("password", "");
            if (username.isEmpty() || password.isEmpty()) {
                Probe.journal(ctx, "selfheal skip: no-local-credentials");
                return false;
            }

            String check = Probe.checkServer(srv, tcpMs, httpMs);
            // 凭据缺失（云端只下发账密、无 token）或凭据失效，都可用账密重登；
            // 服务器不可达/其它故障不在此列（无需登录）。
            if (!check.startsWith("token-invalid:") && !check.equals("not-logged-in")) {
                // token 还有效，或服务器本身不可达：不需要重登
                Probe.journal(ctx, "selfheal skip: probe=" + check);
                return false;
            }

            String[] ep = Probe.endpointOf(srv);
            String base = ep[0] + "://" + ep[1] + ":" + ep[2]
                    + (ep[3].isEmpty() ? "" : "/" + ep[3]);

            JSONObject body = new JSONObject();
            body.put("Username", username);
            body.put("Password", password);
            body.put("Pw", password);

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(base + "/Users/AuthenticateByName").openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setConnectTimeout(Math.max(tcpMs + httpMs, 600));
                conn.setReadTimeout(Math.max(httpMs, 300));
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");

                byte[] payload = body.toString().getBytes(StandardCharsets.UTF_8);
                OutputStream os = conn.getOutputStream();
                os.write(payload);
                os.flush();
                os.close();

                int code = conn.getResponseCode();
                if (code != 200 && code != 201) {
                    Probe.journal(ctx, "selfheal login-failed http " + code);
                    return false;
                }

                InputStream in = conn.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                JSONObject res = new JSONObject(sb.toString());
                String token = res.optString("AccessToken", "");
                String userId = "";
                JSONObject user = res.optJSONObject("User");
                if (user != null) {
                    userId = user.optString("Id", "");
                }
                if (userId.isEmpty()) {
                    userId = res.optString("UserId", "");
                }
                if (token.isEmpty() || userId.isEmpty()) {
                    Probe.journal(ctx, "selfheal bad-response no-token/user");
                    return false;
                }

                boolean ok = persistToken(ctx, srv.optString("id", ""), userId, token);
                Probe.journal(ctx, "selfheal relogin " + (ok ? "ok" : "persist-failed"));
                return ok;
            } finally {
                if (conn != null) {
                    try {
                        conn.disconnect();
                    } catch (Throwable ignored) {
                    }
                }
            }
        } catch (Throwable t) {
            Probe.journal(ctx, "selfheal crash: " + t.getClass().getSimpleName()
                    + " " + t.getMessage());
            return false;
        }
    }

    /**
     * 提示用户需要更新账号（仅由 SyncProvider 在"本地重登失败 + 云端拉取也失败"
     * 双失败后调用）：优先启动弹窗 Activity；若 TV 系统限制后台启动（抛异常），
     * 兜底用 Toast 在主线程显示。
     */
    public static void notifyCredentialOutdated(Context ctx) {
        if (sNotifiedBoot) {
            return;
        }
        sNotifiedBoot = true;
        try {
            Intent i = new Intent(ctx, CredentialNotifyActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(i);
            Probe.journal(ctx, "selfheal notify-activity launched");
        } catch (Throwable t) {
            try {
                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        Toast.makeText(ctx,
                                "登录凭据已失效，自动重登失败，请更新账号",
                                Toast.LENGTH_LONG).show();
                    } catch (Throwable ignored) {
                    }
                });
            } catch (Throwable ignored) {
            }
        }
    }

    /** 把新 token/userId/isVerified 写回 server_configs（语义等同母壳 updateServerLoginInfo） */
    private static boolean persistToken(Context ctx, String serverId, String userId, String token) {
        try {
            SharedPreferences sp = ctx.getSharedPreferences(SERVER_PREFS, 0);
            JSONArray servers = new JSONArray(sp.getString("servers", "[]"));
            boolean changed = false;
            for (int i = 0; i < servers.length(); i++) {
                JSONObject o = servers.optJSONObject(i);
                if (o == null) {
                    continue;
                }
                if (serverId.isEmpty() || serverId.equals(o.optString("id", ""))) {
                    o.put("accessToken", token);
                    o.put("userId", userId);
                    o.put("lastLoginTime", System.currentTimeMillis());
                    o.put("isVerified", true);
                    changed = true;
                    break;
                }
            }
            if (!changed) {
                return false;
            }
            return sp.edit().putString("servers", servers.toString()).commit();
        } catch (Throwable t) {
            return false;
        }
    }
}
