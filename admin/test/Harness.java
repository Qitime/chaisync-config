package com.dh.chaisync.admin;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

/**
 * 交叉验证：用<b>子 APK 的真实源码</b>封装一封，写到文件。
 * 然后由 ParentCheck（母 APK 的真实源码）与 Node（publish.mjs）分别解开。
 */
public final class Harness {

    public static void main(String[] a) throws Exception {
        String pem = new String(Files.readAllBytes(Paths.get(a[0])), "UTF-8");
        String masterB64 = new String(Files.readAllBytes(Paths.get(a[1])), "UTF-8").trim();
        String out = a[2];
        long seq = Long.parseLong(a[3]);

        byte[] master = java.util.Base64.getDecoder().decode(masterB64);

        // 与 Publisher 组装出来的形状一致（含中文，验证 UTF-8 往返）
        String json = "{"
                + "\"kind\":\"accounts\","
                + "\"mode\":\"sync\","
                + "\"seq\":" + seq + ","
                + "\"lastUsedServerId\":\"srv-a1b2c3d4\","
                + "\"servers\":[{"
                + "\"id\":\"srv-a1b2c3d4\","
                + "\"alias\":\"主账号·客厅\","
                + "\"protocol\":\"https\","
                + "\"host\":\"media.example.com\","
                + "\"port\":443,"
                + "\"path\":\"\","
                + "\"username\":\"qiqi\","
                + "\"password\":\"p@ss 中文\","
                + "\"userId\":\"u-001\","
                + "\"accessToken\":\"tok_abcdef123456\","
                + "\"isVerified\":true,"
                + "\"backupRoutes\":[]"
                + "}],"
                + "\"removedIds\":[]"
                + "}";
        byte[] plain = json.getBytes("UTF-8");

        org.json.JSONObject env = EnvelopeSeal.seal(
                plain, master, "k1", seq, EnvelopeSeal.parsePrivatePem(pem), new SecureRandom());

        String text = env.toString();
        Files.write(Paths.get(out), text.getBytes("UTF-8"));

        System.out.println("子 APK seal  → 明文 " + plain.length + "B，信封 " + text.length() + " 字符");
        System.out.println("  公钥指纹 " + EnvelopeSeal.fingerprint(readTrim(a[4])));
    }

    private static String readTrim(String p) throws Exception {
        return new String(Files.readAllBytes(Paths.get(p)), "UTF-8").trim();
    }
}
