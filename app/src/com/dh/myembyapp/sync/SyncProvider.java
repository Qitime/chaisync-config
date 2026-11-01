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
 * <p>启动决策树（本地优先，云端兜底）：
 * <pre>
 *   首次运行（从没同步成功过）        → 阻塞拉取（≤9s）→ 写入 → 进入
 *   已同步过 → 先应用待生效缓存（0ms）
 *        ├ 探测当前服务器 token 有效    → 直接进入（零网络，不做任何拉取）
 *        ├ 探测 token 失效（401/403）  → ① 本地账密重登 → 成功 → 进入
 *        │                               → ② 失败 → 拉云端 → 再试本地
 *        │                               → ③ 双失败 → 弹窗「需更新账号」
 *        └ 服务器不可达 / 非凭据故障    → ① 先本地 failover 切换
 *                                        → ② 全不可达 → 拉云端
 *   拉取失败（镜像全挂 / 信封被拒）    → 保留本地配置进入，原因写 sync_status.txt
 * </pre>
 *
 * <p>避免无效云端拉取的原则：只有「首次运行」或「本地明确无法恢复」（token 失效且
 * 重登失败 / 本地全部服务器不可达）才碰 GitHub；token 有效时不发起任何网络请求。
 *
 * <p>弹窗只在「本地重登失败 + 云端拉取也未能恢复」双失败后触发（见
 * {@link SelfHeal#notifyCredentialOutdated}），避免云端能救回时打扰用户。
 *
 * <p>注意这里<b>没有</b>设备码、没有 HMAC、没有"设备未授权"这条分支：
 * 端点已经是公开文件，鉴权靠签名，不靠设备身份。
 */
public final class SyncProvider extends ContentProvider {

    private static final String TAG = "ChaiSync";

    /** 镜像竞速的阶梯间隔：首个镜像发出 900ms 仍无结果才发下一个（fetchStaggerMs 可覆盖） */
    private static final int DEFAULT_STAGGER_MS = 900;

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
        Store.applyCached(ctx, sync);

        // ============ 首次运行：本地无配置，必须拉云端 ============
        if (firstRun) {
            Probe.journal(ctx, "first run -> blocking fetch");
            pullAndApply(ctx, sync, cfg, cfg.fetchBudgetMs);
            // 多台服务器时筛出最先可用的一台
            Probe.failover(ctx, 700, 1000, 3500);
            // 拉取后若 token 仍失效，用本地账密重登兜底（首次场景不弹窗：
            // 无登录态时 MainActivity 自己会进登录页）
            SelfHeal.tryRelogin(ctx, cfg.probeTcpMs, cfg.probeHttpMs);
            return;
        }

        // ============ 非首次：先探测当前服务器 ============
        String probe = Probe.check(ctx, cfg.probeTcpMs, cfg.probeHttpMs);

        if (probe.startsWith("ok")) {
            // token 有效：零网络直进（不再后台拉取，避免无效请求）
            Probe.journal(ctx, "probe ok (" + probe + ") -> fast path (zero network)");
            return;
        }

        if (probe.startsWith("token-invalid:") || probe.startsWith("not-logged-in")) {
            // 凭据失效（401/403）或凭据缺失（云端只下发账密、无 token）：
            // 先用本地账密重登；成功即直进
            if (healLocal(ctx, cfg)) {
                return;
            }
            // ② 本地重登失败 → 才拉云端（云端可能已下发新账密/新服务器）
            Probe.journal(ctx, "local heal failed -> cloud repair");
            cloudRepair(ctx, sync, cfg);
            // ③ 拉取后再试一次本地（新账密可能已生效）
            if (!healLocal(ctx, cfg)) {
                // 双失败（本地重登失败 + 云端拉取也未能恢复）→ 弹窗提示更新账号
                Probe.journal(ctx, "selfheal double-fail -> notify");
                SelfHeal.notifyCredentialOutdated(ctx);
            }
            return;
        }

        // ============ 服务器不可达 / 非凭据故障（tcp/tls/5xx/slow）============
        Probe.journal(ctx, "probe failed (" + probe + ") -> local failover first");
        // ① 先在本地其它服务器之间切换；一台都不剩才允许拉云端
        String winner = Probe.failover(ctx, 700, 1000, 3500);
        if (winner == null) {
            Probe.journal(ctx, "no usable local server -> cloud repair");
            cloudRepair(ctx, sync, cfg);
            return;
        }
        // ② 切换成功后重新探测当前（新）服务器
        String after = Probe.check(ctx, cfg.probeTcpMs, cfg.probeHttpMs);
        if (after.startsWith("ok")) {
            Probe.journal(ctx, "failover ok -> fast path");
            return;
        }
        if (after.startsWith("token-invalid:") || after.startsWith("not-logged-in")) {
            // 切换后的服务器凭据缺失/失效 → 本地重登
            if (healLocal(ctx, cfg)) {
                return;
            }
            Probe.journal(ctx, "failover token-invalid, local heal failed -> cloud repair");
            cloudRepair(ctx, sync, cfg);
            if (!healLocal(ctx, cfg)) {
                Probe.journal(ctx, "selfheal double-fail (failover) -> notify");
                SelfHeal.notifyCredentialOutdated(ctx);
            }
            return;
        }
        // ③ 切换后仍不可达 → 拉云端
        Probe.journal(ctx, "failover still unreachable -> cloud repair");
        cloudRepair(ctx, sync, cfg);
    }

    /**
     * 本地重登：AuthenticateByName 拿到 200 并写回 token 即视为恢复。
     * 不再用 /Users/Me 复核——部分 Emby 对该端点即使带有效 token 也返回 500
     * （如 embymv.cc:8096），复核会导致登录成功仍误判双失败、弹窗。
     */
    private static boolean healLocal(Context ctx, Store.AppConfig cfg) {
        return SelfHeal.tryRelogin(ctx, cfg.probeTcpMs, cfg.probeHttpMs);
    }

    /** 拉取云端信封 + 应用 + 本地 failover 筛活 */
    private static void cloudRepair(Context ctx, SharedPreferences sync, Store.AppConfig cfg) {
        pullAndApply(ctx, sync, cfg, cfg.fetchBudgetMs);
        Probe.failover(ctx, 700, 1000, 3500);
    }

    /* ------------------------------------------------------------------ */
    /* 拉取 + 校验 + 应用                                                  */
    /* ------------------------------------------------------------------ */

    /**
     * 遍历镜像，逐个验签。任何一层拒绝都只影响这一次尝试，继续换下一个源；
     * 全部失败才保留本地配置。
     *
     * <p>镜像之间是<b>竞速</b>（hedged fetch）而不是串行：第 1 个立即发出，
     * 900ms 内没有结果才发第 2 个，再 900ms 发第 3 个；已发出的请求不取消，
     * 谁先带回完整信封且验签通过就用谁。串行写法在全慢的镜像面前会把 9s 预算
     * 烧光（3 × 3.2s），竞速后最坏 ~4s，典型情况（首个镜像 <1s）不多打镜像站。
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
            boolean[] rb = new boolean[1];
            if (hedgedFetch(ctx, env, cfg, buster, lastSeq, deadline, sync, reasons, rb)) {
                return;
            }
            sawRollback |= rb[0];
        }

        Probe.journal(ctx, "fetch-failed" + reasons + " | keep local");
    }

    /**
     * 一轮竞速拉取 + 验签 + 应用。
     *
     * @param sawRollbackOut 出参：本轮是否见过 rollback（seq 未前进），供外层决定换缓存键重问
     * @return 任一镜像验签通过并完成应用返回 true；预算耗尽/全部被拒返回 false
     */
    private static boolean hedgedFetch(Context ctx, Envelope env, Store.AppConfig cfg,
                                       String buster, long lastSeq, long deadline,
                                       SharedPreferences sync, StringBuilder reasons,
                                       boolean[] sawRollbackOut) {
        int n = cfg.mirrors.length;
        Fetcher.Fetch[] fetches = new Fetcher.Fetch[n];
        int stagger = cfg.fetchStaggerMs > 0 ? cfg.fetchStaggerMs : DEFAULT_STAGGER_MS;
        long round = System.currentTimeMillis();
        int launched = 0;

        try {
            // launching=false 表示预算用完、不再发新镜像，但已发出的请求要继续收完
            boolean launching = true;
            // 排空最多再等 1.2s：足够接住"差一点就回来"的结果，又不至于把首屏拖长
            long drainDeadline = deadline + 1200L;
            while (true) {
                // 阶梯发出：第 i 个镜像在 round + i*stagger 后仍无成功结果才发出
                if (launching && launched < n) {
                    long due = round + (long) launched * stagger;
                    long now = System.currentTimeMillis();
                    // 等阶梯间隔时也要盯着截止时间，否则最后一个镜像会把预算睡穿
                    long sleepFor = Math.min(due - now, deadline - now);
                    if (sleepFor > 0) {
                        Thread.sleep(sleepFor);
                    }
                    int remain = (int) (deadline - System.currentTimeMillis());
                    if (remain < 900) {
                        // 预算用完：停止发新请求，但继续收已发出的——直接 break 会把
                        // "马上就回来"的结果丢掉，表现成无故 fetch-failed。
                        launching = false;
                        reasons.append(" |budget-exhausted");
                    } else {
                        String url = Fetcher.buildUrl(cfg.mirrors[launched], cfg.rawUrl, buster);
                        fetches[launched] = Fetcher.start(url, Math.min(cfg.fetchTryMs, remain));
                        launched++;
                    }
                }

                // 收结果：先到先验签；验签被拒不取消其它在飞的请求
                boolean anyPending = false;
                for (int i = 0; i < launched; i++) {
                    Fetcher.Fetch f = fetches[i];
                    if (f == null || f.taken) {
                        continue;
                    }
                    Fetcher.Result r = f.poll();
                    if (r == null) {
                        anyPending = true;
                        continue;
                    }
                    f.taken = true;
                    String via = hostOf(cfg.mirrors[i]);
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
                                + " kid=" + opened.kid + " applied=" + applied
                                + " (" + r.ms + "ms, " + launched + " raced)");
                        return true;
                    } catch (Envelope.Reject rej) {
                        if ("rollback".equals(rej.code)) {
                            sawRollbackOut[0] = true; // 供外层 pass 循环判断是否换缓存键重问
                        }
                        reasons.append('|').append(via).append(':').append(rej.code);
                        Probe.journal(ctx, "reject " + rej.code + " from " + via
                                + " :: " + rej.getMessage());
                    } catch (Throwable t) {
                        reasons.append('|').append(via).append(":err");
                        Probe.journal(ctx, "verify crashed: " + t);
                    }
                }

                // 退出条件：所有已发出的都收完，且没有新的要发了
                if (!anyPending && (launched >= n || !launching)) {
                    break;
                }
                // 排空阶段的兜底：在飞请求自己的读超时也有限，这里再加一道总闸，
                // 避免某个镜像"连上了但一字节一字节挤"把首屏拖死
                if (!launching && System.currentTimeMillis() > drainDeadline) {
                    reasons.append(" |drain-timeout");
                    break;
                }
                Thread.sleep(60);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // 未完成的抓取让它们自然收尾（守护线程），避免句柄悬挂
            for (int i = 0; i < launched; i++) {
                Fetcher.Fetch f = fetches[i];
                if (f != null && !f.taken) {
                    f.join(250);
                }
            }
        }
        return false;
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
