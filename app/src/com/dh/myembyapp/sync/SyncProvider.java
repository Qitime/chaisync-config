package com.dh.myembyapp.sync;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import org.json.JSONObject;

/**
 * 冷启动账号同步（GitHub 版）。
 *
 * <p>为什么用 ContentProvider：它的 onCreate 早于 Application.onCreate 与 MainActivity.onCreate，
 * 所以这里写进 SharedPreferences 的值，会被<b>同一次启动</b>的首屏直接读到。
 * 这是"改完账号不用重开两次"的前提。
 *
 * <p>启动决策树：
 * <pre>
 *   首次运行（从没同步成功过）        → 阻塞拉取（≤9s）→ 写入 → 进入
 *   已同步过 → 先应用待生效缓存（0ms）
 *        ├ 探测当前服务器通过          → 直接进入；后台顺手拉一次（只更新缓存）
 *        └ 探测失败                    → 阻塞拉取 → 立即写入 → 同一屏用新配置进入
 *   拉取失败（镜像全挂 / 信封被拒）    → 保留本地配置进入，原因写 sync_status.txt
 * </pre>
 *
 * <p>注意这里<b>没有</b>设备码、没有 HMAC、没有"设备未授权"这条分支：
 * 端点已经是公开文件，鉴权靠签名，不靠设备身份。
 */
public final class SyncProvider extends ContentProvider {

    private static final String TAG = "ChaiSync";

    @Override
    public boolean onCreate() {
        final Context ctx = getContext();
        if (ctx == null) {
            return true;
        }
        try {
            run(ctx);
        } catch (Throwable t) {
            Probe.journal(ctx, "crash: " + t.getClass().getSimpleName() + " " + t.getMessage());
        }
        return true;
    }

    /* ------------------------------------------------------------------ */

    private static void run(Context ctx) {
        Store.AppConfig cfg = Store.loadConfig(ctx);
        if (!cfg.enabled) {
            Probe.journal(ctx, "disabled by sync_config.json");
            return;
        }
        if (cfg.mirrors.length == 0 || cfg.rawUrl.isEmpty()
                || cfg.pubKeyHex.isEmpty() || cfg.masterB64.isEmpty()) {
            Probe.journal(ctx, "config incomplete: mirrors=" + cfg.mirrors.length
                    + " rawUrl=" + (cfg.rawUrl.isEmpty() ? "missing" : "ok")
                    + " pubKey=" + (cfg.pubKeyHex.isEmpty() ? "missing" : "ok")
                    + " master=" + (cfg.masterB64.isEmpty() ? "missing" : "ok"));
            return;
        }

        SharedPreferences sync = ctx.getSharedPreferences(Store.SYNC_PREFS, 0);
        boolean firstRun = sync.getLong("last_seq", -1) < 0;

        // 先把上次验签通过但没来得及应用的正文落地（0ms，不联网）
        boolean appliedCache = Store.applyCached(ctx, sync);

        if (firstRun) {
            Probe.journal(ctx, "first run -> blocking fetch");
            pullAndApply(ctx, sync, cfg, cfg.fetchBudgetMs);
            // 第一次下发多台服务器时，默认那台未必可达；在进入首屏前先找一个能用的
            Probe.failover(ctx, 700, 1000, 3500);
            return;
        }

        String probe = Probe.check(ctx, cfg.probeTcpMs, cfg.probeHttpMs);
        if (probe.startsWith("ok")) {
            // 主流路径：服务器好好的，完全不碰 GitHub
            Probe.journal(ctx, "probe ok (" + probe + ") cachedApplied=" + appliedCache + " -> fast path");
            backgroundRefresh(ctx, sync, cfg);
            return;
        }

        Probe.journal(ctx, "probe failed (" + probe + ") -> repair fetch");
        pullAndApply(ctx, sync, cfg, cfg.fetchBudgetMs);
        // 拉取失败（镜像全挂/无新配置）时也要在本地多台服务器之间兜底切换
        Probe.failover(ctx, 700, 1000, 3500);
    }

    /* ------------------------------------------------------------------ */
    /* 拉取 + 校验 + 应用                                                  */
    /* ------------------------------------------------------------------ */

    /**
     * 遍历镜像，逐个验签。任何一层拒绝都只影响这一次尝试，继续换下一个源；
     * 全部失败才保留本地配置。
     *
     * <p>为什么要有第二遍：第一遍的缓存键是 {@code ?v=<lastSeq>}，而 raw 有
     * {@code Cache-Control: max-age=300}（实测），所以刚发布完的 5 分钟内，
     * 这个 URL 仍会返回上一个版本 → 验签通过但 seq 不前进 → 被判 rollback。
     * 这时换一个带时间戳的缓存键重问一遍，就能拿到新的。
     */
    private static void pullAndApply(Context ctx, SharedPreferences sync,
                                     Store.AppConfig cfg, int budgetMs) {
        long lastSeq = sync.getLong("last_seq", 0);

        Envelope env;
        try {
            env = new Envelope(cfg.pubKeyHex, cfg.masterB64);
        } catch (Throwable t) {
            Probe.journal(ctx, "bad-injected-keys: " + t.getMessage());
            return;
        }

        long deadline = System.currentTimeMillis() + Math.max(budgetMs, 1500L);
        boolean sawRollback = false;
        StringBuilder reasons = new StringBuilder();

        for (int pass = 0; pass < 2; pass++) {
            if (pass == 1 && !sawRollback) {
                break;
            }
            String buster = pass == 0 ? String.valueOf(lastSeq) : "t" + System.currentTimeMillis();
            for (String mirror : cfg.mirrors) {
                int remain = (int) (deadline - System.currentTimeMillis());
                if (remain < 900) {
                    reasons.append(" |budget-exhausted");
                    break;
                }
                String url = Fetcher.buildUrl(mirror, cfg.rawUrl, buster);
                Fetcher.Result r = Fetcher.get(url, Math.min(cfg.fetchTryMs, remain));
                String via = hostOf(mirror);

                if (!r.usable()) {
                    reasons.append('|').append(via).append(':').append(r.status)
                            .append(r.detail.isEmpty() ? "" : r.detail);
                    Probe.journal(ctx, "get " + via + " -> " + r.status + " " + r.detail
                            + " (" + r.ms + "ms)");
                    continue;
                }

                try {
                    Envelope.Opened opened = env.open(r.body, lastSeq);
                    Store.saveCached(sync, opened.payload, opened.seq);
                    boolean applied = Store.apply(ctx, sync, opened.payload, opened.seq);
                    Probe.journal(ctx, "verified via=" + via + " seq=" + opened.seq
                            + " kid=" + opened.kid + " applied=" + applied + " (" + r.ms + "ms)");
                    return;
                } catch (Envelope.Reject rej) {
                    if ("rollback".equals(rej.code)) {
                        sawRollback = true;
                    }
                    reasons.append('|').append(via).append(':').append(rej.code);
                    Probe.journal(ctx, "reject " + rej.code + " from " + via + " :: " + rej.getMessage());
                } catch (Throwable t) {
                    reasons.append('|').append(via).append(":err");
                    Probe.journal(ctx, "verify crashed: " + t);
                }
            }
        }

        Probe.journal(ctx, "fetch-failed" + reasons + " | keep local");
    }

    /**
     * 健康路径下的后台拉取：只更新缓存，不动当前生效配置。
     * 用途是"旧服务器还活着，但你想主动换一台"——下次启动生效。
     */
    private static void backgroundRefresh(final Context ctx, final SharedPreferences sync,
                                          final Store.AppConfig cfg) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                pullAndApply(ctx, sync, cfg, 5000);
            }
        }, "chai-sync-bg");
        t.setDaemon(true);
        t.start();
    }

    private static String hostOf(String url) {
        try {
            String s = url.replace("https://", "").replace("http://", "");
            int slash = s.indexOf('/');
            return slash > 0 ? s.substring(0, slash) : s;
        } catch (Throwable t) {
            return "mirror";
        }
    }

    /* ------------------------------------------------------------------ */
    /* ContentProvider 的空实现（本 provider 只借用 onCreate 时机）        */
    /* ------------------------------------------------------------------ */

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
