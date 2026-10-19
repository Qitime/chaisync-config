package com.dh.chaisync.admin;

import android.net.Uri;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * GitHub Contents API 的最小客户端。只用两个动作：读已有文件的 sha、写新文件。
 *
 * <p>为什么用 Contents API 而不是 git push：手机上没有 git，也不该有。
 * Contents API 一次 PUT 就是一个 commit，服务端负责打包对象、更新 ref。
 *
 * <p>三个容易踩的地方：
 * <ol>
 *   <li><b>PUT 必须先有 sha</b>（文件已存在时）。没有 sha 会返回 422 "sha wasn't supplied"。
 *       所以发布流程固定是 GET → 拿 sha → PUT。</li>
 *   <li><b>fine-grained PAT 对无权访问的仓库返回 404 而不是 403</b>。
 *       所以 404 有三种含义：路径不存在、仓库名写错、token 没这个库的权限。报错信息里必须都提。</li>
 *   <li><b>api.github.com 在国内可能不稳</b>。所以 apiBase 是可配的，
 *       可以填反代前缀；但反代是否支持 PUT 要自己验证（很多只放行 GET）。</li>
 * </ol>
 */
public final class GitHubClient {

    public static final class Resp {
        public final int code;
        public final String body;

        Resp(int code, String body) {
            this.code = code;
            this.body = body;
        }

        public boolean ok() {
            return code >= 200 && code < 300;
        }
    }

    private GitHubClient() {
    }

    public static String contentsUrl(String apiBase, String repo, String path) {
        String base = apiBase == null || apiBase.trim().isEmpty()
                ? "https://api.github.com" : apiBase.trim();
        while (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        StringBuilder sb = new StringBuilder(base);
        sb.append("/repos/").append(repo.trim()).append("/contents/");
        String[] parts = path.split("/");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty()) {
                continue;
            }
            if (i > 0 && sb.charAt(sb.length() - 1) != '/') {
                sb.append('/');
            }
            sb.append(Uri.encode(parts[i]));
        }
        return sb.toString();
    }

    /**
     * @param method   GET / PUT
     * @param jsonBody 为 null 表示无 body
     */
    public static Resp call(String method, String url, String token, String jsonBody, int timeoutMs) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(method);
            conn.setConnectTimeout(Math.min(timeoutMs, 10000));
            conn.setReadTimeout(timeoutMs);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Accept", "application/vnd.github+json");
            conn.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
            conn.setRequestProperty("User-Agent", "ChaiSync-Admin/1.0");
            if (jsonBody != null) {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                byte[] b = jsonBody.getBytes(StandardCharsets.UTF_8);
                conn.setFixedLengthStreamingMode(b.length);
                OutputStream os = conn.getOutputStream();
                try {
                    os.write(b);
                } finally {
                    os.close();
                }
            }

            int code = conn.getResponseCode();
            InputStream in = code >= 400 ? conn.getErrorStream() : conn.getInputStream();
            String body = in == null ? "" : readAll(in);
            return new Resp(code, body);
        } catch (Throwable t) {
            return new Resp(-1, t.getClass().getSimpleName() + ": " + t.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /** 从 GET 响应里取 sha 与 content；文件不存在返回 null。 */
    public static Remote getRemote(String apiBase, String repo, String path,
                                   String branch, String token, int timeoutMs) throws Exception {
        String url = contentsUrl(apiBase, repo, path) + "?ref=" + Uri.encode(branch);
        Resp r = call("GET", url, token, null, timeoutMs);
        if (r.code == 404) {
            return null;
        }
        if (!r.ok()) {
            throw new IllegalStateException(describeFailure(r, "读取远端文件"));
        }
        JSONObject j = new JSONObject(r.body);
        Remote m = new Remote();
        m.sha = j.optString("sha", "");
        String b64 = j.optString("content", "").replaceAll("\\s", "");
        if (!b64.isEmpty()) {
            try {
                m.raw = android.util.Base64.decode(b64, android.util.Base64.DEFAULT);
            } catch (Throwable ignored) {
                m.raw = null;
            }
        }
        return m;
    }

    public static final class Remote {
        public String sha;
        public byte[] raw;
    }

    public static String describeFailure(Resp r, String action) {
        String hint = "";
        if (r.code == 401) {
            hint = "token 无效或已过期";
        } else if (r.code == 403) {
            hint = "权限不足，或触发了频率限制";
        } else if (r.code == 404) {
            hint = "仓库/路径不存在，或 token 没有这个仓库的 Contents 权限（fine-grained token 无权时返回 404 而不是 403）";
        } else if (r.code == 422) {
            hint = "缺少 sha 或内容不合法";
        } else if (r.code == -1) {
            hint = "网络不通，或被墙（本机对 api.github.com 常不可达，可换 apiBase）";
        }
        String msg = brief(r.body);
        return action + "失败 HTTP " + r.code + "：" + hint + (msg.isEmpty() ? "" : " ｜ " + msg);
    }

    /** 从 GitHub 的错误体里抠出 message，避免把整段 JSON 糊到界面上。 */
    public static String brief(String body) {
        if (body == null || body.isEmpty()) {
            return "";
        }
        try {
            JSONObject j = new JSONObject(body);
            String m = j.optString("message", "");
            if (!m.isEmpty()) {
                String docs = j.optString("documentation_url", "");
                return docs.isEmpty() ? m : m + " (" + docs + ")";
            }
        } catch (Throwable ignored) {
        }
        String s = body.replaceAll("\\s+", " ").trim();
        return s.length() > 180 ? s.substring(0, 180) + "…" : s;
    }

    private static String readAll(InputStream in) throws Exception {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
            return out.toString("UTF-8");
        } finally {
            in.close();
        }
    }
}
