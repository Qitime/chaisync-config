package com.dh.myembyapp.sync;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 配置读取与 prefs 落盘。
 *
 * <p>写入语义（与原版 App 自带的 WebDAV 同步不同，这里是有意设计的）：
 * <ul>
 *   <li>按 id upsert：云端有的覆盖本地同 id 条目，本地独有账号保留</li>
 *   <li>取消勾选的账号（曾在 managed_ids 里、这次不在下发列表）从电视删除</li>
 *   <li>云端下发空 backupRoutes 时<b>不清空</b>电视上本地配的备用线路</li>
 * </ul>
 *
 * <p>三个 seq 的分工，别混：
 * <pre>
 *   last_seq      已接受过的最大 seq —— 防回滚基线，只增不减
 *   cached_seq    已验签解密、尚未应用的正文
 *   applied_seq   已真正写进 server_configs 的正文
 * </pre>
 */
public final class Store {

    static final String SYNC_PREFS = "sync_settings";
    static final String SERVER_PREFS = "server_configs";

    private static final String[] COPY_FIELDS = {
            "alias", "protocol", "host", "port", "path", "username", "password",
            "userId", "accessToken", "isVerified", "directOnly", "enableStrmDirectPlay",
            "trustAllCerts", "note", "lastLoginTime", "backupRoutes", "activeBackupRouteId",
            "customIconPath", "customIconSourceUrl", "customIconName", "deviceId",
            "lastPlaybackAccessTime"
    };

    private Store() {
    }

    /* ------------------------------------------------------------------ */
    /* 内置参数（assets/sync_config.json，由 build.ps1 注入）               */
    /* ------------------------------------------------------------------ */

    public static final class AppConfig {
        public boolean enabled = true;
        public String[] mirrors = new String[0];
        public String rawUrl = "";
        public String pubKeyHex = "";
        public String masterB64 = "";
        public int probeTcpMs = 1200;
        public int probeHttpMs = 1500;
        public int fetchBudgetMs = 9000;
        public int fetchTryMs = 3200;
    }

    public static AppConfig loadConfig(Context ctx) {
        AppConfig c = new AppConfig();
        try {
            InputStream in = ctx.getAssets().open("sync_config.json");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[2048];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
            in.close();

            JSONObject o = new JSONObject(out.toString("UTF-8"));
            c.enabled = o.optBoolean("enabled", true);
            c.rawUrl = o.optString("rawUrl", "");
            c.pubKeyHex = o.optString("pubKeyHex", "");
            c.masterB64 = o.optString("masterB64", "");
            c.probeTcpMs = o.optInt("probeTcpMs", 1200);
            c.probeHttpMs = o.optInt("probeHttpMs", 1500);
            c.fetchBudgetMs = o.optInt("fetchBudgetMs", 9000);
            c.fetchTryMs = o.optInt("fetchTryMs", 3200);

            JSONArray arr = o.optJSONArray("mirrors");
            if (arr != null) {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < arr.length(); i++) {
                    String m = arr.optString(i, "").trim();
                    if (!m.isEmpty()) {
                        list.add(m);
                    }
                }
                c.mirrors = list.toArray(new String[0]);
            }
        } catch (Throwable t) {
            Probe.journal(ctx, "config-missing-or-bad: " + t.getClass().getSimpleName());
        }
        return c;
    }

    /* ------------------------------------------------------------------ */
    /* 应用                                                                */
    /* ------------------------------------------------------------------ */

    public static boolean apply(Context ctx, SharedPreferences sync, JSONObject payload, long seq) {
        if ("off".equals(payload.optString("mode", "sync"))) {
            Probe.journal(ctx, "skip: mode=off");
            return false;
        }

        JSONArray servers = payload.optJSONArray("servers");
        if (servers == null || servers.length() == 0) {
            Probe.journal(ctx, "skip: no account selected");
            return false;
        }
        if (seq == sync.getLong("applied_seq", -1)) {
            Probe.journal(ctx, "unchanged seq=" + seq);
            return false;
        }

        SharedPreferences sp = ctx.getSharedPreferences(SERVER_PREFS, 0);
        JSONArray existing;
        try {
            existing = new JSONArray(sp.getString("servers", "[]"));
        } catch (Throwable t) {
            existing = new JSONArray();
        }

        LinkedHashMap<String, JSONObject> byId = new LinkedHashMap<String, JSONObject>();
        for (int i = 0; i < existing.length(); i++) {
            JSONObject o = existing.optJSONObject(i);
            if (o != null) {
                byId.put(o.optString("id"), o);
            }
        }

        List<String> desired = new ArrayList<String>();
        Set<String> desiredSet = new HashSet<String>();
        for (int i = 0; i < servers.length(); i++) {
            JSONObject o = servers.optJSONObject(i);
            if (o == null) {
                continue;
            }
            String id = o.optString("id", "");
            if (id.isEmpty()) {
                // id 缺失会被 Gson 解析成空条目，列表里显示为一行空白
                continue;
            }
            desired.add(id);
            desiredSet.add(id);
            JSONObject target = byId.get(id);
            if (target == null) {
                target = new JSONObject();
            }
            try {
                target.put("id", id);
            } catch (Throwable ignored) {
            }
            merge(target, o);
            byId.put(id, target);
        }

        Set<String> remove = new HashSet<String>();
        JSONArray removedIds = payload.optJSONArray("removedIds");
        if (removedIds != null) {
            for (int i = 0; i < removedIds.length(); i++) {
                remove.add(removedIds.optString(i));
            }
        }
        JSONArray managed;
        try {
            managed = new JSONArray(sync.getString("managed_ids", "[]"));
        } catch (Throwable t) {
            managed = new JSONArray();
        }
        for (int i = 0; i < managed.length(); i++) {
            String id = managed.optString(i, "");
            if (!id.isEmpty() && !desiredSet.contains(id)) {
                remove.add(id);
            }
        }

        JSONArray result = new JSONArray();
        for (Map.Entry<String, JSONObject> e : byId.entrySet()) {
            if (desiredSet.contains(e.getKey()) || remove.contains(e.getKey())) {
                continue;
            }
            result.put(e.getValue());
        }
        for (String id : desired) {
            result.put(byId.get(id));
        }

        JSONArray order = new JSONArray();
        for (String id : desired) {
            order.put(id);
        }
        for (int i = 0; i < result.length(); i++) {
            JSONObject o = result.optJSONObject(i);
            if (o == null) {
                continue;
            }
            String id = o.optString("id", "");
            if (!desiredSet.contains(id)) {
                order.put(id);
            }
        }

        String last = payload.optString("lastUsedServerId", "");
        if (last.isEmpty() || !desiredSet.contains(last)) {
            last = desired.get(0);
        }

        boolean ok = sp.edit()
                .putString("servers", result.toString())
                .putString("server_order", order.toString())
                .putString("last_used_server", last)
                .commit();

        sync.edit()
                .putString("managed_ids", new JSONArray(desired).toString())
                .putLong("applied_seq", seq)
                .commit();

        Probe.journal(ctx, "applied seq=" + seq + " servers=" + result.length()
                + " default=" + last + " committed=" + ok);
        return true;
    }

    private static void merge(JSONObject target, JSONObject source) {
        for (String key : COPY_FIELDS) {
            try {
                if (!source.has(key) || source.isNull(key)) {
                    continue;
                }
                if ("backupRoutes".equals(key)) {
                    JSONArray routes = source.optJSONArray(key);
                    if (routes != null && routes.length() == 0) {
                        // 云端不下发备用线路时，别把电视上本地配的线路抹掉
                        continue;
                    }
                }
                target.put(key, source.get(key));
            } catch (Throwable ignored) {
            }
        }
    }

    /* ------------------------------------------------------------------ */
    /* 缓存                                                                */
    /* ------------------------------------------------------------------ */

    public static void saveCached(SharedPreferences sync, JSONObject payload, long seq) {
        sync.edit()
                .putString("cached_payload", payload.toString())
                .putLong("cached_seq", seq)
                .putLong("last_seq", seq)
                .putLong("last_fetch_at", System.currentTimeMillis())
                .commit();
    }

    /** 把上次验签通过、但尚未应用的正文落地（0ms，不联网） */
    public static boolean applyCached(Context ctx, SharedPreferences sync) {
        String cached = sync.getString("cached_payload", null);
        if (cached == null) {
            return false;
        }
        long cachedSeq = sync.getLong("cached_seq", -1);
        long appliedSeq = sync.getLong("applied_seq", -1);
        if (cachedSeq <= appliedSeq) {
            return false;
        }
        try {
            return apply(ctx, sync, new JSONObject(cached), cachedSeq);
        } catch (Throwable t) {
            return false;
        }
    }

    public static boolean hasUsableLocalAccount(Context ctx) {
        try {
            JSONArray servers = new JSONArray(ctx.getSharedPreferences(SERVER_PREFS, 0)
                    .getString("servers", "[]"));
            for (int i = 0; i < servers.length(); i++) {
                JSONObject o = servers.optJSONObject(i);
                if (o == null) {
                    continue;
                }
                if (!o.optString("userId", "").isEmpty()
                        && !o.optString("accessToken", "").isEmpty()) {
                    return true;
                }
            }
        } catch (Throwable ignored) {
        }
        return false;
    }
}
