package com.dh.chaisync.admin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 一次发布的完整编排：读远端 → 定 seq → 封装 → 自检 → 推送。
 *
 * <p>顺序是刻意的，不能重排：
 * <pre>
 *   GET 远端 sha ──┬─ 拿到 → 解密取 seq，用于对齐
 *                 └─ 404  → 首次发布，sha 留空
 *   seq = max(本地, 远端) + 1        ← 防止两台设备/两个工具各自发布时撞号
 *   组装 payload（seq 同时写进正文）
 *   seal（AES-GCM + ECDSA）
 *   openSelf（用公钥验签 + 用 master 解密，逐字节比对）
 *   PUT（带 sha，一个 commit）
 *   本地 localSeq = seq
 * </pre>
 *
 * <p>为什么 seq 要取 max：如果只用本地计数器，换手机重新粘贴 bootstrap 后 localSeq 归零，
 * 下一次发布就会推出一个小 seq，所有电视会把它判成 rollback 并<b>永久拒绝更新</b>，
 * 而现象是"什么都不发生"。这个错误必须在工具层消灭，不能指望使用者记得。
 */
public final class Publisher {

    public static final class Outcome {
        public long seq;
        public int servers;      // 本次实际推送的台数
        public int total;        // 本地服务器总数
        public boolean partial;  // true = 只推送了勾选的一部分
        public int plainBytes;
        public int envelopeBytes;
        public String commit = "";
        public String sha = "";
        public boolean firstPublish;
        public final List<String> warnings = new ArrayList<String>();
    }

    public interface Progress {
        void step(String message);
    }

    private Publisher() {
    }

    public static Outcome publish(JSONObject state, Progress cb) throws Exception {
        JSONObject boot = state.optJSONObject("bootstrap");
        if (boot == null) {
            throw new IllegalStateException("还没有导入发布凭据");
        }

        String token = boot.optString("token", "").trim();
        String repo = boot.optString("repo", "").trim();
        String branch = boot.optString("branch", "main").trim();
        String path = boot.optString("path", "dist/config.enc.json").trim();
        String apiBase = boot.optString("apiBase", "https://api.github.com").trim();
        String kid = boot.optString("kid", "k1").trim();
        String masterB64 = boot.optString("masterB64", "").trim();
        String pubHex = boot.optString("pubKeyHex", "").trim();

        if (repo.isEmpty() || !repo.contains("/")) {
            throw new IllegalStateException("仓库要写成 owner/repo");
        }
        if (token.isEmpty()) {
            throw new IllegalStateException("没有 GitHub token");
        }
        if (!EnvelopeSeal.looksLikePem(boot.optString("signingPem", ""))) {
            throw new IllegalStateException("signing.pem 缺失或格式不对");
        }

        byte[] master = android.util.Base64.decode(masterB64, android.util.Base64.DEFAULT);
        if (master.length != 32) {
            throw new IllegalStateException("master 必须是 32 字节（base64 后 44 字符），实际 " + master.length);
        }
        PrivateKey priv = EnvelopeSeal.parsePrivatePem(boot.getString("signingPem"));

        JSONArray servers = state.optJSONArray("servers");
        if (servers == null || servers.length() == 0) {
            throw new IllegalStateException("至少要有一台服务器才能发布（母 APK 收到空列表会跳过）");
        }

        // 勾选语义：只推送选中的条目。未勾选的服务器不会被写进信封，
        // 母 APK 收到 partial=true 时也不会按 managed_ids 做差集删除。
        boolean partial = false;
        int total = 0;
        for (int i = 0; i < servers.length(); i++) {
            JSONObject s = servers.optJSONObject(i);
            if (s == null || s.optString("id", "").isEmpty()) {
                continue;
            }
            total++;
            if (!isSelected(s)) {
                partial = true;
            }
        }
        if (total == 0) {
            throw new IllegalStateException("没有有效服务器条目（id 不能为空）");
        }

        // ① 读远端，拿 sha 与已有 seq
        cb.step("读取远端信封…");
        GitHubClient.Remote remote = GitHubClient.getRemote(apiBase, repo, path, branch, token, 15000);
        long remoteSeq = 0;
        if (remote != null && remote.raw != null) {
            try {
                JSONObject old = new JSONObject(new String(remote.raw, "UTF-8"));
                remoteSeq = old.optLong("seq", 0);
            } catch (Throwable ignored) {
                // 远端是一段坏 JSON（手工改过 / 别的东西占了这个路径），按 0 处理并在下面告警
                remoteSeq = 0;
            }
        }

        long localSeq = state.optLong("localSeq", 0);
        long seq = Math.max(localSeq, remoteSeq) + 1;
        if (remoteSeq > localSeq) {
            cb.step("远端 seq=" + remoteSeq + " 比本地大，按远端对齐");
        }

        // ② 组装正文（只放勾选项）
        JSONArray clean = new JSONArray();
        int missingToken = 0;
        for (int i = 0; i < servers.length(); i++) {
            JSONObject s = servers.optJSONObject(i);
            if (s == null) {
                continue;
            }
            String id = s.optString("id", "");
            if (id.isEmpty() || !isSelected(s)) {
                continue;
            }
            clean.put(normalize(s));
            if (s.optString("accessToken", "").isEmpty() || s.optString("userId", "").isEmpty()) {
                missingToken++;
            }
        }
        if (clean.length() == 0) {
            throw new IllegalStateException("一台服务器都没勾选，先勾选要推送的服务器");
        }
        if (missingToken > 0) {
            final String w = missingToken + " 台已勾选服务器还没有 accessToken，"
                    + "电视会写入但登录失败，建议先点「刷新选中」";
            cb.step(w);
        }

        // 当前使用中的服务器：部分推送时如果它没被勾选，也不要强行改成第一台，
        // 否则电视会因为你这次只推了别的服务器而被动换服。
        String last = state.optString("lastUsedServerId", "");
        boolean lastInAll = false;
        for (int i = 0; i < servers.length(); i++) {
            JSONObject s = servers.optJSONObject(i);
            if (s != null && last.equals(s.optString("id", ""))) {
                lastInAll = true;
                break;
            }
        }
        if (!partial || !lastInAll) {
            boolean lastValid = false;
            for (int i = 0; i < clean.length(); i++) {
                if (last.equals(clean.optJSONObject(i).optString("id", ""))) {
                    lastValid = true;
                    break;
                }
            }
            if (!lastValid) {
                last = clean.optJSONObject(0).optString("id", "");
            }
        }

        JSONArray removed = new JSONArray();
        JSONArray pendingRemoved = state.optJSONArray("pendingRemoved");
        if (pendingRemoved != null) {
            for (int i = 0; i < pendingRemoved.length(); i++) {
                String id = pendingRemoved.optString(i, "");
                if (!id.isEmpty()) {
                    removed.put(id);
                }
            }
        }

        JSONObject payload = new JSONObject();
        payload.put("kind", "accounts");
        payload.put("mode", "sync");
        payload.put("seq", seq);
        payload.put("partial", partial);
        payload.put("lastUsedServerId", last);
        payload.put("servers", clean);
        payload.put("removedIds", removed);

        byte[] plain = payload.toString().getBytes("UTF-8");

        // ③ 封装
        cb.step("加密与签名…");
        JSONObject env = EnvelopeSeal.seal(plain, master, kid, seq, priv, new SecureRandom());

        // ④ 自检：这是推送前唯一能拦住"电视静默不更新"的关口
        cb.step("本地自检（验签 + 解密）…");
        byte[] back = EnvelopeSeal.openSelf(env, master, pubHex);
        if (!Arrays.equals(back, plain)) {
            throw new IllegalStateException("自检失败：解出的正文与原文不一致");
        }
        JSONObject backJson = new JSONObject(new String(back, "UTF-8"));
        if (backJson.optLong("seq", -1) != seq) {
            throw new IllegalStateException("自检失败：内外 seq 不一致");
        }

        // ⑤ 推送
        cb.step("推送到 GitHub…");
        JSONObject body = new JSONObject();
        body.put("message", "chaisync: publish seq=" + seq + " (" + clean.length()
                + (partial ? "/" + total : "") + " servers" + (partial ? ", partial" : "") + ")");
        body.put("content", android.util.Base64.encodeToString(
                env.toString().getBytes("UTF-8"), android.util.Base64.NO_WRAP));
        body.put("branch", branch);
        if (remote != null && remote.sha != null && !remote.sha.isEmpty()) {
            body.put("sha", remote.sha);
        }

        GitHubClient.Resp r = GitHubClient.call("PUT",
                GitHubClient.contentsUrl(apiBase, repo, path), token, body.toString(), 25000);
        if (!r.ok()) {
            throw new IllegalStateException(GitHubClient.describeFailure(r, "推送"));
        }

        Outcome o = new Outcome();
        o.seq = seq;
        o.servers = clean.length();
        o.total = total;
        o.partial = partial;
        o.plainBytes = plain.length;
        o.envelopeBytes = env.toString().getBytes("UTF-8").length;
        o.firstPublish = remote == null;
        try {
            JSONObject resp = new JSONObject(r.body);
            JSONObject commit = resp.optJSONObject("commit");
            if (commit != null) {
                o.commit = commit.optString("sha", "");
            }
            JSONObject content = resp.optJSONObject("content");
            if (content != null) {
                o.sha = content.optString("sha", "");
            }
        } catch (Throwable ignored) {
        }

        state.put("localSeq", seq);
        // 删除动作已经随本次信封下发，清掉待删列表；失败时保留，下次重试
        state.put("pendingRemoved", new JSONArray());
        JSONObject lastPub = new JSONObject();
        lastPub.put("seq", seq);
        lastPub.put("at", System.currentTimeMillis());
        lastPub.put("commit", o.commit);
        lastPub.put("bytes", o.envelopeBytes);
        state.put("lastPublish", lastPub);

        return o;
    }

    /** 旧状态里没有 selected 字段，一律按“选中”处理，避免升级后默认全不选。 */
    private static boolean isSelected(JSONObject s) {
        return s == null || s.optBoolean("selected", true);
    }

    /**
     * 只保留母 APK 的 Store.COPY_FIELDS 认识的字段。
     * 多余字段虽然会被忽略，但会让信封变大、也会让"看到的东西"和"实际生效的东西"不一致。
     */
    private static JSONObject normalize(JSONObject s) throws Exception {
        JSONObject o = new JSONObject();
        o.put("id", s.optString("id", ""));
        copy(s, o, "alias");
        copy(s, o, "protocol");
        copy(s, o, "host");
        copy(s, o, "port");
        copy(s, o, "path");
        copy(s, o, "username");
        copy(s, o, "password");
        copy(s, o, "userId");
        copy(s, o, "accessToken");
        copy(s, o, "isVerified");
        copy(s, o, "directOnly");
        copy(s, o, "enableStrmDirectPlay");
        copy(s, o, "trustAllCerts");
        copy(s, o, "note");
        copy(s, o, "lastLoginTime");
        copy(s, o, "backupRoutes");
        copy(s, o, "activeBackupRouteId");
        copy(s, o, "deviceId");
        copy(s, o, "lastPlaybackAccessTime");
        // 母 APK 的白名单里没有它，但这个字段原版 App 会读，留着无害且能保住行为
        copy(s, o, "useEmbyPrefix");
        return o;
    }

    private static void copy(JSONObject from, JSONObject to, String key) {
        try {
            if (!from.has(key) || from.isNull(key)) {
                return;
            }
            to.put(key, from.get(key));
        } catch (Throwable ignored) {
        }
    }
}
