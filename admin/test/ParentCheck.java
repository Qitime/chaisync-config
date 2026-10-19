package com.dh.myembyapp.sync;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 交叉验证：用<b>母 APK 的真实源码</b>（Envelope.java）解子 APK 发出的信封。
 * 这是"子母 APK 能不能配对"的唯一硬证据。
 */
public final class ParentCheck {

    public static void main(String[] a) throws Exception {
        String text = new String(Files.readAllBytes(Paths.get(a[0])), "UTF-8");
        String pubHex = new String(Files.readAllBytes(Paths.get(a[1])), "UTF-8").trim();
        String masterB64 = new String(Files.readAllBytes(Paths.get(a[2])), "UTF-8").trim();
        long lastSeq = a.length > 3 ? Long.parseLong(a[3]) : 0;

        Envelope env = new Envelope(pubHex, masterB64);
        Envelope.Opened o = env.open(text, lastSeq);

        org.json.JSONArray servers = o.payload.optJSONArray("servers");
        System.out.println("母 APK open  → OK  seq=" + o.seq + " kid=" + o.kid
                + " servers=" + (servers == null ? 0 : servers.length()));
        System.out.println("  alias=" + servers.optJSONObject(0).optString("alias", "?")
                + "  host=" + servers.optJSONObject(0).optString("host", "?"));
    }
}
