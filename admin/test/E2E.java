package com.dh.chaisync.admin;

import com.dh.myembyapp.sync.Envelope;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * 端到端验证：用本地 HTTP 服务器冒充 GitHub Contents API，跑真实的 {@link Publisher}。
 *
 * <p>要验的四件事，任何一件错了都会在真机上表现为"电视静默不更新"：
 * <ol>
 *   <li>首次发布（远端 404）能成，且 seq 从 1 开始</li>
 *   <li>二次发布带 sha 覆盖，seq 递增</li>
 *   <li>换手机后本地 seq=0 但远端已是 N，发布必须得 N+1（否则全体判 rollback）</li>
 *   <li>推上去的密文，母 APK 的 Envelope 能解；篡改与重放会被拒</li>
 * </ol>
 */
public final class E2E {

    private static byte[] remoteFile;
    private static String remoteSha;
    private static int rev;

    public static void main(String[] a) throws Exception {
        String pem = read(a[0]);
        String masterB64 = read(a[1]).trim();
        String pubHex = read(a[2]).trim();

        HttpServer srv = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        srv.createContext("/repos/", new Gh());
        srv.start();
        int port = ((InetSocketAddress) srv.getAddress()).getPort();
        String apiBase = "http://127.0.0.1:" + port;
        System.out.println("模拟 GitHub API: " + apiBase + "\n");

        JSONObject boot = new JSONObject();
        boot.put("repo", "someone/private-repo");
        boot.put("branch", "main");
        boot.put("path", "dist/config.enc.json");
        boot.put("apiBase", apiBase);
        boot.put("token", "fake_pat");
        boot.put("kid", "k1");
        boot.put("masterB64", masterB64);
        boot.put("signingPem", pem);
        boot.put("pubKeyHex", pubHex);

        // ---- 第 1 次：首次发布 ----
        JSONObject st = state(boot, 0);
        System.out.println("[1] 首次发布");
        Publisher.Outcome o1 = Publisher.publish(st, log());
        check("seq=1", o1.seq == 1, "实际 " + o1.seq);
        check("firstPublish", o1.firstPublish, "");
        check("本地 localSeq 已更新", st.optLong("localSeq", 0) == 1, "");

        // ---- 第 2 次：覆盖 ----
        System.out.println("\n[2] 二次发布（带 sha 覆盖）");
        Publisher.Outcome o2 = Publisher.publish(st, log());
        check("seq=2", o2.seq == 2, "实际 " + o2.seq);
        check("远端 sha 已变化", remoteSha != null, "");

        // ---- 第 3 次：模拟换手机，本地 seq=0，远端 seq=2 ----
        System.out.println("\n[3] 换手机（localSeq=0，远端 seq=2）");
        JSONObject fresh = state(boot, 0);
        Publisher.Outcome o3 = Publisher.publish(fresh, log());
        check("seq 对齐到 3", o3.seq == 3, "实际 " + o3.seq + "（若为 1 则电视会永久拒绝更新）");

        // ---- 第 4 次：母 APK 解推上去的那封 ----
        System.out.println("\n[4] 母 APK 解最终信封");
        String text = new String(remoteFile, "UTF-8");
        Envelope env = new Envelope(pubHex, masterB64);
        Envelope.Opened opened = env.open(text, 0);
        check("seq 一致", opened.seq == 3, "实际 " + opened.seq);
        JSONArray servers = opened.payload.optJSONArray("servers");
        check("servers=2", servers != null && servers.length() == 2,
                "实际 " + (servers == null ? "null" : servers.length()));
        check("中文别名完好",
                "主账号·客厅".equals(servers.optJSONObject(0).optString("alias", "")),
                servers.optJSONObject(0).optString("alias", ""));
        check("password 送达", "p@ss 中文".equals(
                servers.optJSONObject(0).optString("password", "")), "");
        check("seq 写在正文里", opened.payload.optLong("seq", -1) == 3, "");

        // ---- 攻击面 ----
        System.out.println("\n[5] 攻击测试");
        JSONObject e = new JSONObject(text);

        JSONObject tampered = new JSONObject(text);
        String ct = e.getString("ct");
        char ch = ct.charAt(5);
        tampered.put("ct", ct.substring(0, 5) + (ch == 'A' ? 'B' : 'A') + ct.substring(6));
        expectReject(env, tampered.toString(), 0, "bad-signature", "改密文一个字符");

        expectReject(env, text, 3, "rollback", "重放旧 seq（lastSeq=3）");

        JSONObject wrongKid = new JSONObject(text);
        wrongKid.put("kid", "k9");
        expectReject(env, wrongKid.toString(), 0, "bad-signature", "改 kid");

        // 用错误的 master 解（模拟没有密钥的人拿到密文）
        try {
            new Envelope(pubHex, Base64.getEncoder().encodeToString(new byte[32])).open(text, 0);
            System.out.println("  ✗ 错误 master 竟然解开了");
        } catch (Envelope.Reject r) {
            check("错误 master 被拒", "bad-master".equals(r.code), r.code);
        }

        srv.stop(0);
        System.out.println("\n全部通过。");
    }

    /* ------------------------------------------------------------------ */

    private static JSONObject state(JSONObject boot, long localSeq) throws Exception {
        JSONObject st = new JSONObject();
        st.put("v", 1);
        st.put("bootstrap", boot);
        st.put("localSeq", localSeq);
        st.put("lastUsedServerId", "srv-a1b2c3d4");

        JSONArray arr = new JSONArray();
        arr.put(server("srv-a1b2c3d4", "主账号·客厅", "media.example.com", "tok_aaaa1111"));
        arr.put(server("srv-e5f6g7h8", "备用", "backup.example.com", "tok_bbbb2222"));
        st.put("servers", arr);
        return st;
    }

    private static JSONObject server(String id, String alias, String host, String token) throws Exception {
        JSONObject s = new JSONObject();
        s.put("id", id);
        s.put("alias", alias);
        s.put("protocol", "https");
        s.put("host", host);
        s.put("port", 443);
        s.put("path", "");
        s.put("username", "qiqi");
        s.put("password", "p@ss 中文");
        s.put("userId", "u-" + id);
        s.put("accessToken", token);
        s.put("isVerified", true);
        s.put("backupRoutes", new JSONArray());
        return s;
    }

    private static Publisher.Progress log() {
        return new Publisher.Progress() {
            @Override
            public void step(String message) {
                System.out.println("      · " + message);
            }
        };
    }

    private static void expectReject(Envelope env, String text, long lastSeq,
                                     String wantCode, String label) throws Exception {
        try {
            env.open(text, lastSeq);
            System.out.println("  ✗ " + label + "：没有被拦住");
        } catch (Envelope.Reject r) {
            check(label + " → " + r.code, wantCode.equals(r.code), "期望 " + wantCode);
        }
    }

    private static void check(String label, boolean ok, String extra) {
        System.out.println("  " + (ok ? "✓" : "✗") + " " + label
                + (extra.isEmpty() ? "" : "  (" + extra + ")"));
        if (!ok) {
            throw new AssertionError("失败：" + label);
        }
    }

    private static String read(String p) throws Exception {
        return new String(Files.readAllBytes(Paths.get(p)), "UTF-8");
    }

    /* ------------------------------------------------------------------ */

    static final class Gh implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) {
            try {
                String method = ex.getRequestMethod();
                if ("GET".equals(method)) {
                    if (remoteFile == null) {
                        send(ex, 404, "{\"message\":\"Not Found\"}");
                        return;
                    }
                    JSONObject j = new JSONObject();
                    j.put("sha", remoteSha);
                    j.put("content", Base64.getMimeEncoder().encodeToString(remoteFile));
                    send(ex, 200, j.toString());
                } else if ("PUT".equals(method)) {
                    JSONObject req = new JSONObject(readAll(ex.getRequestBody()));
                    String sha = req.optString("sha", "");
                    if (remoteFile != null && !sha.equals(remoteSha)) {
                        send(ex, 409, "{\"message\":\"sha mismatch\"}");
                        return;
                    }
                    remoteFile = Base64.getDecoder().decode(req.optString("content", ""));
                    remoteSha = "blob" + (++rev);
                    JSONObject resp = new JSONObject();
                    JSONObject commit = new JSONObject();
                    commit.put("sha", "commit" + rev);
                    resp.put("commit", commit);
                    JSONObject content = new JSONObject();
                    content.put("sha", remoteSha);
                    resp.put("content", content);
                    send(ex, 200, resp.toString());
                } else {
                    send(ex, 405, "{\"message\":\"method not allowed\"}");
                }
            } catch (Throwable t) {
                try {
                    send(ex, 500, "{\"message\":\"" + t.getClass().getSimpleName() + "\"}");
                } catch (Throwable ignored) {
                }
            }
        }

        private static void send(HttpExchange ex, int code, String body) throws Exception {
            byte[] b = body.getBytes("UTF-8");
            ex.getResponseHeaders().add("Content-Type", "application/json");
            ex.sendResponseHeaders(code, b.length);
            OutputStream os = ex.getResponseBody();
            os.write(b);
            os.close();
        }

        private static String readAll(InputStream in) throws Exception {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
            return out.toString("UTF-8");
        }
    }
}
