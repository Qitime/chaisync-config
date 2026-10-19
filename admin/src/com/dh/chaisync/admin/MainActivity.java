package com.dh.chaisync.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 子 APK 的唯一界面。全部用 framework API 代码构建，不用 XML 布局、不引 AndroidX。
 *
 * <p>理由不是"炫技"，是构建条件：这个 APK 要在没有 Gradle、没有网、没有依赖缓存的环境里
 * 用 aapt2 + javac + d8 + apksigner 打出来。多一个 androidx 依赖，整条链路就断了。
 *
 * <p>界面只有三件事：管服务器、刷令牌、发布。没有设置页、没有导航、没有多级菜单——
 * 因为它的使用者只有一个人，用的频率是"换账号时"。
 */
public final class MainActivity extends Activity {

    private static final int C_BG = 0xFF0E1015;
    private static final int C_CARD = 0xFF181B22;
    private static final int C_CHIP = 0xFF232833;
    private static final int C_TEXT = 0xFFE9ECF3;
    private static final int C_DIM = 0xFF8790A3;
    private static final int C_FAINT = 0xFF5A6274;
    private static final int C_ACCENT = 0xFF4F8CFF;
    private static final int C_OK = 0xFF3DD68C;
    private static final int C_WARN = 0xFFFFB020;
    private static final int C_ERR = 0xFFFF6B6B;

    private static final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private static final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    private JSONObject state;
    private LinearLayout listBox;
    private TextView statusTitle;
    private TextView statusDetail;
    private TextView seqBadge;
    private TextView logView;
    private ScrollView logScroll;
    private View publishBtn;
    private View refreshBtn;
    private TextView selectInfo;
    private final Handler ui = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(C_BG);
        getWindow().setNavigationBarColor(C_BG);

        boolean broken = false;
        String brokenMsg = null;
        try {
            state = Vault.load(this);
        } catch (Throwable t) {
            broken = true;
            brokenMsg = t.getMessage();
            state = Vault.emptyState();
        }

        buildUi();
        render();

        if (broken) {
            log("保险库打不开：" + brokenMsg);
            new AlertDialog.Builder(this)
                    .setTitle("本地数据无法解密")
                    .setMessage("这不是网络问题。保险库密钥绑在本机 Keystore 上，"
                            + "恢复备份或换机后旧密文必然解不开。\n\n"
                            + "需要重新导入一次 bootstrap JSON。旧数据已被隔离，点「清空」后重新导入。")
                    .setPositiveButton("清空并重新导入", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int w) {
                            Vault.wipe(MainActivity.this);
                            state = Vault.emptyState();
                            render();
                            showBootstrapDialog();
                        }
                    })
                    .setNegativeButton("先不动", null)
                    .show();
        } else if (!Vault.isReady(state)) {
            log("等待导入发布凭据");
            showBootstrapDialog();
        } else {
            log("就绪：" + Vault.describe(state));
        }
    }

    /* ------------------------------------------------------------------ */
    /* 界面骨架                                                            */
    /* ------------------------------------------------------------------ */

    private void buildUi() {
        ScrollView sv = new ScrollView(this);
        sv.setBackgroundColor(C_BG);
        sv.setFillViewport(true);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(26), dp(18), dp(30));
        sv.addView(root, new ViewGroup.LayoutParams(MP, WC));

        TextView title = tv("ChaiSync", 27, C_TEXT, true);
        root.addView(title);
        TextView sub = tv("配置发布端 · 在本机加密后推送到 GitHub" + versionTag(), 12.5f, C_DIM, false);
        sub.setPadding(0, dp(5), 0, dp(20));
        root.addView(sub);

        root.addView(buildStatusCard());
        root.addView(sectionTitle("服务器"));

        listBox = new LinearLayout(this);
        listBox.setOrientation(LinearLayout.VERTICAL);
        root.addView(listBox, new ViewGroup.LayoutParams(MP, WC));

        LinearLayout selRow = new LinearLayout(this);
        selRow.setOrientation(LinearLayout.HORIZONTAL);
        selRow.setGravity(Gravity.CENTER_VERTICAL);
        selRow.setPadding(0, 0, 0, dp(8));

        selectInfo = tv("已选 0 / 0", 11.5f, C_DIM, false);
        selRow.addView(selectInfo, new LinearLayout.LayoutParams(0, WC, 1f));

        TextView allBtn = chip("全选", C_CHIP, C_DIM, 11, dp(6), dp(10));
        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAll(true);
            }
        });
        selRow.addView(allBtn, new ViewGroup.LayoutParams(WC, WC));

        TextView noneBtn = chip("全不选", C_CHIP, C_DIM, 11, dp(6), dp(10));
        noneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAll(false);
            }
        });
        LinearLayout.LayoutParams nlp = new LinearLayout.LayoutParams(WC, WC);
        nlp.leftMargin = dp(8);
        selRow.addView(noneBtn, nlp);
        root.addView(selRow);

        TextView addBtn = chip("+ 新增服务器", C_CHIP, C_TEXT, 13, dp(10), dp(14));
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editServer(-1);
            }
        });
        LinearLayout addRow = new LinearLayout(this);
        addRow.setPadding(0, dp(4), 0, dp(16));
        addRow.addView(addBtn, new ViewGroup.LayoutParams(WC, WC));
        root.addView(addRow);

        root.addView(actionButtons());
        root.addView(sectionTitle("日志"));

        logView = tv("", 11, C_DIM, false);
        logView.setTypeface(Typeface.MONOSPACE);
        logView.setLineSpacing(dp(3), 1f);
        logView.setTextIsSelectable(true);

        logScroll = new ScrollView(this);
        logScroll.setBackground(round(C_CARD, 14));
        logScroll.setPadding(dp(14), dp(12), dp(14), dp(12));
        logScroll.addView(logView, new ViewGroup.LayoutParams(MP, WC));
        root.addView(logScroll, new ViewGroup.LayoutParams(MP, dp(150)));

        // 这里必须真正挂到窗口上。少了这一行，整棵树只存在于内存里：
        // 所有 setText / 点击回调照常执行，但屏幕上永远是一片底色，
        // 只有 AlertDialog 能看见——表现就是"导入成功却没有管理页面"。
        sv.setFitsSystemWindows(true);
        setContentView(sv);
    }

    private View buildStatusCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackground(round(C_CARD, 16));
        card.setPadding(dp(16), dp(15), dp(16), dp(15));

        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);

        statusTitle = tv("未初始化", 15, C_TEXT, true);
        top.addView(statusTitle, new LinearLayout.LayoutParams(0, WC, 1f));

        seqBadge = tv("seq 0", 11, C_DIM, false);
        seqBadge.setBackground(round(C_CHIP, 8));
        seqBadge.setPadding(dp(8), dp(3), dp(8), dp(3));
        top.addView(seqBadge, new ViewGroup.LayoutParams(WC, WC));
        card.addView(top);

        statusDetail = tv("", 11.5f, C_DIM, false);
        statusDetail.setPadding(0, dp(8), 0, 0);
        statusDetail.setLineSpacing(dp(4), 1f);
        card.addView(statusDetail);

        TextView hint = tv("长按此卡片可重新导入或清空", 10.5f, C_FAINT, false);
        hint.setPadding(0, dp(9), 0, 0);
        card.addView(hint);

        card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showResetMenu();
                return true;
            }
        });

        LinearLayout wrap = new LinearLayout(this);
        wrap.setPadding(0, 0, 0, dp(20));
        wrap.addView(card, new ViewGroup.LayoutParams(MP, WC));
        return wrap;
    }

    private View actionButtons() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(0, 0, 0, dp(22));

        TextView refresh = chip("刷新选中", C_CHIP, C_TEXT, 13, dp(12), dp(14));
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshAll();
            }
        });
        refreshBtn = refresh;
        LinearLayout.LayoutParams lp1 =
                new LinearLayout.LayoutParams(0, WC, 1f);
        lp1.rightMargin = dp(10);
        row.addView(refresh, lp1);

        TextView pub = chip("发布选中", C_ACCENT, 0xFFFFFFFF, 13, dp(12), dp(14));
        pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPublish();
            }
        });
        publishBtn = pub;
        row.addView(pub, new LinearLayout.LayoutParams(0, WC, 1f));
        return row;
    }

    private TextView sectionTitle(String s) {
        TextView t = tv(s.toUpperCase(Locale.US), 10.5f, C_FAINT, true);
        t.setLetterSpacing(0.12f);
        t.setPadding(dp(2), 0, 0, dp(9));
        return t;
    }

    /* ------------------------------------------------------------------ */
    /* 渲染                                                                */
    /* ------------------------------------------------------------------ */

    private void render() {
        boolean ready = Vault.isReady(state);
        listBox.removeAllViews();
        // render() 会被反复调用（导入成功、保存、删除后各一次）。
        // 监听器必须先清掉，否则上一次"未初始化"时挂上的点击事件会留下来，
        // 变成已就绪状态点标题又弹出导入框。
        statusTitle.setOnClickListener(null);

        if (ready) {
            JSONObject boot = state.optJSONObject("bootstrap");
            statusTitle.setText("已就绪");
            statusTitle.setTextColor(C_TEXT);
            seqBadge.setText("seq " + state.optLong("localSeq", 0));
            statusDetail.setText(
                    boot.optString("repo", "?") + "  @" + boot.optString("branch", "main") + "\n"
                            + boot.optString("path", "?") + "\n"
                            + "kid " + boot.optString("kid", "k1")
                            + " · 公钥 " + EnvelopeSeal.fingerprint(boot.optString("pubKeyHex", "")));
            statusDetail.setTextColor(C_DIM);
        } else {
            statusTitle.setText("未初始化");
            statusTitle.setTextColor(C_WARN);
            seqBadge.setText("seq 0");
            statusDetail.setText("还没有导入发布凭据。点这里或长按卡片导入 bootstrap JSON。");
            statusDetail.setTextColor(C_DIM);
            statusTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBootstrapDialog();
                }
            });
        }

        if (publishBtn != null) {
            publishBtn.setAlpha(ready ? 1f : 0.45f);
            publishBtn.setEnabled(ready);
        }

        JSONArray servers = state.optJSONArray("servers");
        if (servers == null || servers.length() == 0) {
            TextView empty = tv("还没有服务器。点下面「+ 新增服务器」。", 12.5f, C_FAINT, false);
            empty.setPadding(dp(4), dp(2), 0, dp(10));
            listBox.addView(empty);
            updateSelectionUi();
            return;
        }

        for (int i = 0; i < servers.length(); i++) {
            JSONObject s = servers.optJSONObject(i);
            if (s == null) {
                continue;
            }
            listBox.addView(serverCard(s, i));
        }
        updateSelectionUi();
    }

    /** 旧状态没有 selected 字段时按选中处理。 */
    private boolean isSelected(JSONObject s) {
        return s != null && s.optBoolean("selected", true);
    }

    private void selectedCounts(int[] out) {
        int total = 0;
        int sel = 0;
        JSONArray arr = state.optJSONArray("servers");
        if (arr != null) {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject s = arr.optJSONObject(i);
                if (s == null || s.optString("id", "").isEmpty()) {
                    continue;
                }
                total++;
                if (isSelected(s)) {
                    sel++;
                }
            }
        }
        out[0] = sel;
        out[1] = total;
    }

    private void updateSelectionUi() {
        int[] c = new int[2];
        selectedCounts(c);
        int sel = c[0];
        int total = c[1];
        if (selectInfo != null) {
            selectInfo.setText("已选 " + sel + " / " + total);
            selectInfo.setTextColor(sel == 0 ? C_WARN : C_DIM);
        }
        if (refreshBtn != null) {
            ((TextView) refreshBtn).setText("刷新选中 (" + sel + ")");
            refreshBtn.setEnabled(sel > 0);
            refreshBtn.setAlpha(sel > 0 ? 1f : 0.45f);
        }
        if (publishBtn != null) {
            boolean ready = Vault.isReady(state);
            ((TextView) publishBtn).setText("发布选中 (" + sel + ")");
            publishBtn.setEnabled(ready && sel > 0);
            publishBtn.setAlpha(ready && sel > 0 ? 1f : 0.45f);
        }
    }

    private void selectAll(boolean on) {
        JSONArray arr = state.optJSONArray("servers");
        if (arr == null) {
            return;
        }
        try {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject s = arr.optJSONObject(i);
                if (s != null) {
                    s.put("selected", on);
                }
            }
            persist();
            render();
        } catch (Throwable t) {
            toast("保存勾选状态失败：" + t.getMessage());
        }
    }

    private View serverCard(final JSONObject s, final int index) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackground(round(C_CARD, 14));
        card.setPadding(dp(15), dp(13), dp(15), dp(11));

        LinearLayout.LayoutParams cardLp = new LinearLayout.LayoutParams(MP, WC);
        cardLp.bottomMargin = dp(10);

        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);

        final CheckBox cb = new CheckBox(this);
        cb.setChecked(isSelected(s));
        cb.setButtonTintList(ColorStateList.valueOf(C_ACCENT));
        cb.setPadding(0, 0, dp(7), 0);
        cb.setMinWidth(0);
        cb.setMinimumWidth(0);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    s.put("selected", isChecked);
                    persist();
                    updateSelectionUi();
                } catch (Throwable t) {
                    toast("保存勾选状态失败：" + t.getMessage());
                }
            }
        });
        top.addView(cb, new ViewGroup.LayoutParams(WC, WC));

        String alias = s.optString("alias", "");
        if (alias.isEmpty()) {
            alias = s.optString("host", "(未命名)");
        }
        TextView name = tv(alias, 14.5f, C_TEXT, true);
        top.addView(name, new LinearLayout.LayoutParams(0, WC, 1f));

        boolean hasToken = !s.optString("accessToken", "").isEmpty();
        TextView dot = tv(hasToken ? "已登录" : "未登录", 10.5f,
                hasToken ? C_OK : C_WARN, false);
        dot.setBackground(round(hasToken ? 0x1F3DD68C : 0x1FFFB020,
                hasToken ? C_OK : C_WARN, 1, 7));
        dot.setPadding(dp(8), dp(2), dp(8), dp(2));
        top.addView(dot, new ViewGroup.LayoutParams(WC, WC));
        card.addView(top);

        // 带上协议前缀：443 和 8096 看起来都只是"一个端口"，
        // 但 http 打 443 只会超时，卡片上不写清楚就得靠回忆。
        TextView url = tv(s.optString("protocol", "https") + "://" + EmbyClient.displayUrl(s)
                        + (s.optString("username", "").isEmpty() ? "" : "  ·  " + s.optString("username")),
                11.5f, C_DIM, false);
        url.setPadding(0, dp(6), 0, 0);
        card.addView(url);

        String last = s.optLong("lastLoginTime", 0) > 0
                ? "token 刷新于 " + ago(s.optLong("lastLoginTime", 0)) : "从未刷新过 token";
        TextView meta = tv(last, 10.5f, C_FAINT, false);
        meta.setPadding(0, dp(4), 0, dp(10));
        card.addView(meta);

        LinearLayout ops = new LinearLayout(this);
        ops.setOrientation(LinearLayout.HORIZONTAL);

        TextView test = chip("测试", C_CHIP, C_DIM, 11.5f, dp(7), dp(12));
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testServer(s);
            }
        });
        ops.addView(test, new LinearLayout.LayoutParams(WC, WC));

        TextView edit = chip("编辑", C_CHIP, C_DIM, 11.5f, dp(7), dp(12));
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editServer(index);
            }
        });
        LinearLayout.LayoutParams elp = new LinearLayout.LayoutParams(WC, WC);
        elp.leftMargin = dp(8);
        ops.addView(edit, elp);

        TextView up = chip("上移", C_CHIP, C_DIM, 11, dp(6), dp(9));
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveServer(index, -1);
            }
        });
        LinearLayout.LayoutParams ulp = new LinearLayout.LayoutParams(WC, WC);
        ulp.leftMargin = dp(8);
        ops.addView(up, ulp);

        TextView down = chip("下移", C_CHIP, C_DIM, 11, dp(6), dp(9));
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveServer(index, 1);
            }
        });
        LinearLayout.LayoutParams dlp2 = new LinearLayout.LayoutParams(WC, WC);
        dlp2.leftMargin = dp(8);
        ops.addView(down, dlp2);

        TextView del = chip("删除", C_CHIP, C_ERR, 11.5f, dp(7), dp(12));
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete(s, index);
            }
        });
        LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(WC, WC);
        dlp.leftMargin = dp(8);
        ops.addView(del, dlp);

        card.addView(ops);

        LinearLayout wrap = new LinearLayout(this);
        wrap.addView(card, new ViewGroup.LayoutParams(MP, WC));
        wrap.setLayoutParams(cardLp);
        return wrap;
    }

    /* ------------------------------------------------------------------ */
    /* 服务器增删改                                                        */
    /* ------------------------------------------------------------------ */

    /** 一条链接拆出来的东西。字段语义见 {@link #parseServerUrl}。 */
    private static final class Parsed {
        String scheme;   // "http" / "https"；链接里没写就是 null
        String host;     // 纯主机名，IPv6 不带方括号
        int port;        // 显式端口；没写就是 0
        String path;     // 已去掉首尾斜杠
    }

    /**
     * 把一条人写的地址拆成四个字段。
     *
     * <p>现实中粘进来的东西是五花八门的：{@code https://192.168.22.2:443}、
     * {@code emby.example.com:8096/emby}、{@code http://[fe80::1]:8096}、
     * 甚至带 {@code /web/index.html} 的网页地址。逐字手填四个框，
     * 每个框都是一次出错机会（协议写成 htps、端口漏一位）。
     *
     * <p>故意<b>不</b>在没写端口时补 443/80：Emby 的惯用端口是 8096/8920，
     * 按 URL 标准补默认端口反而会把用户原来的 8096 悄悄改掉。
     */
    private static Parsed parseServerUrl(String raw) {
        if (raw == null) {
            return null;
        }
        String s = raw.trim();
        // 聊天软件里复制出来的地址常带引号或尖括号
        while (s.length() >= 2) {
            char a = s.charAt(0);
            char b = s.charAt(s.length() - 1);
            boolean qa = a == '"' || a == '\'' || a == '<' || a == '(';
            boolean qb = b == '"' || b == '\'' || b == '>' || b == ')';
            if (qa && qb) {
                s = s.substring(1, s.length() - 1).trim();
            } else {
                break;
            }
        }
        if (s.isEmpty()) {
            return null;
        }

        Parsed p = new Parsed();
        int schemeEnd = s.indexOf("://");
        if (schemeEnd > 0) {
            String sch = s.substring(0, schemeEnd).toLowerCase(Locale.US);
            if ("http".equals(sch) || "https".equals(sch)) {
                p.scheme = sch;
            }
            s = s.substring(schemeEnd + 3);
        }

        // user@host 里的认证信息直接丢掉，Emby 用不着
        int at = s.indexOf('@');
        int firstSlash = s.indexOf('/');
        if (at >= 0 && (firstSlash < 0 || at < firstSlash)) {
            s = s.substring(at + 1);
        }

        String authority = s;
        String pathPart = "";
        int iSlash = s.indexOf('/');
        if (iSlash >= 0) {
            authority = s.substring(0, iSlash);
            pathPart = s.substring(iSlash + 1);
        }
        int q = authority.indexOf('?');
        if (q >= 0) {
            authority = authority.substring(0, q);
        }
        int h = pathPart.indexOf('?');
        if (h >= 0) {
            pathPart = pathPart.substring(0, h);
        }
        h = pathPart.indexOf('#');
        if (h >= 0) {
            pathPart = pathPart.substring(0, h);
        }

        String hostPart;
        int port = 0;
        if (authority.startsWith("[")) {                 // [fe80::1]:8096
            int rb = authority.indexOf(']');
            if (rb > 0) {
                hostPart = authority.substring(1, rb);
                String rest = authority.substring(rb + 1);
                if (rest.startsWith(":")) {
                    port = portOf(rest.substring(1));
                }
            } else {
                hostPart = authority;
            }
        } else if (colons(authority) > 1) {               // 裸 IPv6，别把末段当端口
            hostPart = authority;
        } else {
            int c = authority.lastIndexOf(':');
            if (c > 0) {
                int maybe = portOf(authority.substring(c + 1));
                if (maybe > 0) {
                    hostPart = authority.substring(0, c);
                    port = maybe;
                } else {
                    hostPart = authority;
                }
            } else {
                hostPart = authority;
            }
        }

        hostPart = hostPart.trim();
        if (hostPart.isEmpty() || hostPart.indexOf(' ') >= 0) {
            return null;
        }
        p.host = hostPart;
        p.port = port;

        String path = pathPart.trim();
        while (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        // "web/index.html" 是 Emby 的网页入口，不是 API 的 base path
        if (path.equals("web") || path.startsWith("web/")) {
            path = "";
        } else {
            int cut = path.indexOf('/');
            if (cut > 0) {
                path = path.substring(0, cut);
            }
        }
        p.path = path;
        return p;
    }

    private static int colons(String s) {
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ':') {
                n++;
            }
        }
        return n;
    }

    private static int portOf(String s) {
        if (s == null || s.isEmpty() || s.length() > 5) {
            return 0;
        }
        int v = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return 0;
            }
            v = v * 10 + (c - '0');
        }
        return (v > 0 && v <= 65535) ? v : 0;
    }

    /** 把解析结果写回四个输入框。没解析出来的项保持原值，不清空。 */
    private void applyParsed(Parsed p, EditText etHost, EditText etPort, EditText etPath,
                             String[] proto, TextView protoBtn) {
        if (p == null || p.host == null || p.host.isEmpty()) {
            return;
        }
        etHost.setText(p.host);
        etHost.setSelection(etHost.getText().length());
        if (p.scheme != null) {
            proto[0] = p.scheme;
            protoBtn.setText("协议：" + proto[0]);
        }
        if (p.port > 0) {
            etPort.setText(String.valueOf(p.port));
        }
        if (p.path != null && !p.path.isEmpty()) {
            etPath.setText(p.path);
        }
    }

    private String clipboardText() {
        try {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cd = cm.getPrimaryClip();
            if (cd != null && cd.getItemCount() > 0) {
                CharSequence t = cd.getItemAt(0).coerceToText(this);
                if (t != null) {
                    return t.toString().trim();
                }
            }
        } catch (Throwable ignored) {
        }
        return "";
    }

    private void editServer(final int index) {
        final boolean isNew = index < 0;
        JSONArray servers = state.optJSONArray("servers");
        final JSONObject s;
        if (isNew) {
            s = new JSONObject();
        } else {
            JSONObject o = servers == null ? null : servers.optJSONObject(index);
            if (o == null) {
                return;
            }
            s = o;
        }

        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(20), dp(8), dp(20), dp(4));

        final EditText etAlias = field(box, "别名", s.optString("alias", ""), InputType.TYPE_CLASS_TEXT);
        final EditText etHost = field(box, "主机（域名或 IP）", s.optString("host", ""), InputType.TYPE_CLASS_TEXT);
        final EditText etPort = field(box, "端口", String.valueOf(s.optInt("port", 8096)),
                InputType.TYPE_CLASS_NUMBER);
        final EditText etPath = field(box, "路径（可空，如 emby）", s.optString("path", ""),
                InputType.TYPE_CLASS_TEXT);
        final EditText etUser = field(box, "用户名", s.optString("username", ""), InputType.TYPE_CLASS_TEXT);
        final EditText etPass = field(box, "密码", s.optString("password", ""),
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        final EditText etNote = field(box, "备注（可空）", s.optString("note", ""), InputType.TYPE_CLASS_TEXT);

        // 协议用两态切换，避免手打错 "https" 这种低级但致命的错
        final String[] proto = {s.optString("protocol", "https")};
        final TextView protoBtn = chip("协议：" + proto[0], C_CHIP, C_TEXT, 12.5f, dp(8), dp(13));

        // 顶部「从链接解析」：手里多半只有一条 https://host:port/path 的整链。
        // 让它一次把协议、主机、端口、路径填满，比逐框手输少四次出错机会。
        TextView parseBtn = chip("从链接解析", C_CHIP, C_TEXT, 12, dp(9), dp(13));
        TextView parseHint = tv("https://192.168.22.2:443/emby", 10.5f, C_FAINT, false);
        parseHint.setPadding(dp(8), 0, 0, 0);
        LinearLayout parseRow = new LinearLayout(this);
        parseRow.setOrientation(LinearLayout.HORIZONTAL);
        parseRow.setGravity(Gravity.CENTER_VERTICAL);
        parseRow.setPadding(0, 0, 0, dp(8));
        parseRow.addView(parseBtn, new ViewGroup.LayoutParams(WC, WC));
        parseRow.addView(parseHint, new LinearLayout.LayoutParams(0, WC, 1f));
        box.addView(parseRow, 0);

        parseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 只解析主机框自己的内容；只有它是空的时候才去读剪贴板。
                // 不搞"谁信息多就用谁"——那样会把手敲的域名悄悄换成剪贴板里的旧链接。
                Parsed p = parseServerUrl(text(etHost));
                if (p == null) {
                    p = parseServerUrl(clipboardText());
                }
                if (p == null) {
                    toast("主机框是空的，剪贴板里也没有可用地址");
                    return;
                }
                applyParsed(p, etHost, etPort, etPath, proto, protoBtn);
                String path = p.path == null ? "" : p.path;
                toast("已解析 " + p.host + (p.port > 0 ? ":" + p.port : "（端口未变）"));
                log("解析地址：" + p.host + (p.port > 0 ? ":" + p.port : "")
                        + " · " + proto[0] + (path.isEmpty() ? "" : " /" + path));
            }
        });

        // 新增时四个框都还是空的，剪贴板里若躺着一条完整链接就直接填好——
        // 这才是真正的"一键"：复制地址 → 打开新增 → 已经是填好的状态。
        // 只认带协议或端口的字符串，避免把剪贴板里的普通文字误当地址。
        if (isNew) {
            Parsed pre = parseServerUrl(clipboardText());
            if (pre != null && (pre.scheme != null || pre.port > 0)) {
                applyParsed(pre, etHost, etPort, etPath, proto, protoBtn);
                log("已从剪贴板填入：" + pre.host
                        + (pre.port > 0 ? ":" + pre.port : ""));
            }
        }

        protoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proto[0] = "https".equals(proto[0]) ? "http" : "https";
                ((TextView) v).setText("协议：" + proto[0]);
            }
        });
        LinearLayout protoRow = new LinearLayout(this);
        protoRow.setPadding(0, dp(4), 0, dp(10));
        protoRow.addView(protoBtn, new ViewGroup.LayoutParams(WC, WC));
        // 不带索引，追加到末尾。之前写成 addView(protoRow, 1)，
        // 把协议按钮硬插到第 2 位，正好夹在"别名"标签和输入框之间。
        box.addView(protoRow);

        ScrollView sv = new ScrollView(this);
        sv.addView(box, new ViewGroup.LayoutParams(MP, WC));

        final AlertDialog dlg = new AlertDialog.Builder(this)
                .setTitle(isNew ? "新增服务器" : "编辑服务器")
                .setView(sv)
                .setPositiveButton("保存", null)
                .setNegativeButton("取消", null)
                .create();
        dlg.show();
        // 系统的 Material 深色对话框底色是 #424242，跟本 App 的 #0E1015 差得远，
        // 灰底上再叠一片浅灰标签，屏幕上就是一团糊。把窗口底色换掉。
        if (dlg.getWindow() != null) {
            dlg.getWindow().setBackgroundDrawable(new ColorDrawable(C_BG));
            dlg.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        dlg.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 兜底：有人会把整条链接粘进主机框，然后不点解析直接保存。
                // 只要里面真的带了协议或端口，就当场拆开，绝不把整串写进 host。
                Parsed auto = parseServerUrl(text(etHost));
                if (auto != null && (auto.scheme != null || auto.port > 0)) {
                    applyParsed(auto, etHost, etPort, etPath, proto, protoBtn);
                }
                String host = text(etHost).trim();
                if (host.isEmpty()) {
                    toast("主机不能为空");
                    return;
                }
                String protoVal = proto[0];
                int port = 0;
                try {
                    port = Integer.parseInt(text(etPort).trim());
                } catch (Throwable ignored) {
                }
                if (port <= 0 || port > 65535) {
                    toast("端口必须是 1-65535");
                    return;
                }
                try {
                    JSONObject o = new JSONObject();
                    String id = s.optString("id", "");
                    if (id.isEmpty()) {
                        id = "srv-" + Long.toHexString(new SecureRandom().nextLong()).substring(0, 8);
                    }
                    o.put("id", id);
                    o.put("alias", fallback(text(etAlias).trim(), host));
                    o.put("protocol", protoVal);
                    o.put("host", host);
                    o.put("port", port);
                    o.put("path", text(etPath).trim());
                    o.put("username", text(etUser).trim());
                    o.put("password", text(etPass));
                    o.put("note", text(etNote).trim());

                    if (isNew) {
                        o.put("userId", "");
                        o.put("accessToken", "");
                        o.put("isVerified", false);
                        o.put("lastLoginTime", 0L);
                        o.put("directOnly", false);
                        o.put("enableStrmDirectPlay", true);
                        o.put("useEmbyPrefix", false);
                        o.put("trustAllCerts", false);
                        o.put("backupRoutes", new JSONArray());
                        o.put("selected", true);
                    } else {
                        copyIfPresent(s, o, "userId");
                        copyIfPresent(s, o, "accessToken");
                        copyIfPresent(s, o, "isVerified");
                        copyIfPresent(s, o, "lastLoginTime");
                        copyIfPresent(s, o, "directOnly");
                        copyIfPresent(s, o, "enableStrmDirectPlay");
                        copyIfPresent(s, o, "useEmbyPrefix");
                        copyIfPresent(s, o, "trustAllCerts");
                        copyIfPresent(s, o, "backupRoutes");
                        copyIfPresent(s, o, "deviceId");
                        copyIfPresent(s, o, "selected");
                    }

                    JSONArray servers = state.optJSONArray("servers");
                    if (servers == null) {
                        servers = new JSONArray();
                        state.put("servers", servers);
                    }
                    if (isNew) {
                        servers.put(o);
                        if (state.optString("lastUsedServerId", "").isEmpty()) {
                            state.put("lastUsedServerId", id);
                        }
                    } else {
                        servers.put(index, o);
                    }
                    persist();
                    render();
                    log((isNew ? "已新增 " : "已修改 ") + o.optString("alias"));
                    dlg.dismiss();
                } catch (Throwable t) {
                    toast("保存失败：" + t.getMessage());
                }
            }
        });
    }

    private void confirmDelete(final JSONObject s, final int index) {
        new AlertDialog.Builder(this)
                .setTitle("删除「" + fallback(s.optString("alias"), s.optString("host")) + "」")
                .setMessage("下一次发布会把这条从电视上移除，同时删掉电视上的这个服务器配置。\n\n"
                        + "删除会在下次发布时随信封的 removedIds 一起下发，即使这次只勾选了别的服务器也会生效。")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int w) {
                        JSONArray arr = state.optJSONArray("servers");
                        if (arr == null) {
                            return;
                        }
                        JSONArray next = new JSONArray();
                        for (int i = 0; i < arr.length(); i++) {
                            if (i != index) {
                                next.put(arr.optJSONObject(i));
                            }
                        }
                        try {
                            state.put("servers", next);
                            JSONArray pending = state.optJSONArray("pendingRemoved");
                            if (pending == null) {
                                pending = new JSONArray();
                                state.put("pendingRemoved", pending);
                            }
                            String removedId = s.optString("id", "");
                            if (!removedId.isEmpty()) {
                                pending.put(removedId);
                            }
                            if (next.length() > 0
                                    && s.optString("id").equals(state.optString("lastUsedServerId"))) {
                                state.put("lastUsedServerId",
                                        next.optJSONObject(0).optString("id", ""));
                            }
                            persist();
                            render();
                            log("已删除 " + fallback(s.optString("alias"), s.optString("host"))
                                    + "（待下次发布移除）");
                        } catch (Throwable t) {
                            toast("删除失败：" + t.getMessage());
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 上移/下移一位。数组顺序就是发布顺序，母 APK 的 server_order 会按这个顺序重写。
     * 只交换相邻两项，不做“置顶”，避免误触把列表翻乱。
     */
    private void moveServer(int index, int delta) {
        JSONArray arr = state.optJSONArray("servers");
        if (arr == null) {
            return;
        }
        int to = index + delta;
        if (index < 0 || to < 0 || index >= arr.length() || to >= arr.length()) {
            return;
        }
        JSONObject moving = arr.optJSONObject(index);
        JSONObject other = arr.optJSONObject(to);
        if (moving == null || other == null) {
            return;
        }
        try {
            JSONArray next = new JSONArray();
            for (int i = 0; i < arr.length(); i++) {
                if (i == index) {
                    next.put(other);
                } else if (i == to) {
                    next.put(moving);
                } else {
                    next.put(arr.optJSONObject(i));
                }
            }
            state.put("servers", next);
            persist();
            render();
            log("排序：" + fallback(moving.optString("alias"), moving.optString("host"))
                    + (delta < 0 ? " 上移" : " 下移"));
        } catch (Throwable t) {
            toast("排序失败：" + t.getMessage());
        }
    }

    /* ------------------------------------------------------------------ */
    /* 网络动作                                                            */
    /* ------------------------------------------------------------------ */

    private void testServer(final JSONObject s) {
        final ProgressDialog pd = busy("正在连接 " + EmbyClient.displayUrl(s) + "…");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String info = EmbyClient.probe(s, 8000);
                    ui.post(new Runnable() {
                        @Override
                        public void run() {
                            dismiss(pd);
                            toast("通了：" + info);
                            log("测试成功 " + EmbyClient.displayUrl(s) + " → " + info);
                        }
                    });
                } catch (final Throwable t) {
                    ui.post(new Runnable() {
                        @Override
                        public void run() {
                            dismiss(pd);
                            toast("连不上：" + t.getMessage());
                            log("测试失败 " + EmbyClient.displayUrl(s) + " :: " + t.getMessage());
                        }
                    });
                }
            }
        }, "probe").start();
    }

    private void refreshAll() {
        JSONArray servers = state.optJSONArray("servers");
        if (servers == null || servers.length() == 0) {
            toast("还没有服务器");
            return;
        }
        int[] c = new int[2];
        selectedCounts(c);
        if (c[0] == 0) {
            toast("先勾选要刷新的服务器");
            return;
        }
        final ProgressDialog pd = busy("正在刷新选中的 " + c[0] + " 台…");
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONArray arr = state.optJSONArray("servers");
                int ok = 0;
                int fail = 0;
                int skipped = 0;
                final StringBuilder detail = new StringBuilder();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject s = arr.optJSONObject(i);
                    if (s == null || !isSelected(s)) {
                        continue;
                    }
                    String label = fallback(s.optString("alias"), s.optString("host"));
                    if (s.optString("username", "").isEmpty() || s.optString("password", "").isEmpty()) {
                        skipped++;
                        detail.append("· ").append(label).append("：没填账号密码，跳过\n");
                        continue;
                    }
                    try {
                        EmbyClient.Auth a = EmbyClient.authenticate(s, 12000);
                        s.put("accessToken", a.token);
                        s.put("userId", a.userId);
                        s.put("isVerified", true);
                        s.put("lastLoginTime", System.currentTimeMillis());
                        ok++;
                        detail.append("· ").append(label).append("：OK\n");
                    } catch (Throwable t) {
                        fail++;
                        detail.append("· ").append(label).append("：")
                                .append(t.getMessage()).append('\n');
                    }
                }
                try {
                    persist();
                } catch (Throwable ignored) {
                }
                final int fok = ok;
                final int ffail = fail;
                final int fskip = skipped;
                ui.post(new Runnable() {
                    @Override
                    public void run() {
                        dismiss(pd);
                        render();
                        log("刷新完成：" + fok + " 成功 / " + ffail + " 失败"
                                + (fskip > 0 ? " / " + fskip + " 跳过" : ""));
                        for (String line : detail.toString().split("\n")) {
                            if (!line.trim().isEmpty()) {
                                log("  " + line);
                            }
                        }
                        if (fok == 0) {
                            toast("选中的一台都没成功，先检查账号密码与连通性");
                        }
                    }
                });
            }
        }, "refresh").start();
    }

    private void confirmPublish() {
        JSONObject boot = state.optJSONObject("bootstrap");
        if (boot == null) {
            showBootstrapDialog();
            return;
        }
        int[] c = new int[2];
        selectedCounts(c);
        int n = c[1];
        int sel = c[0];
        if (n == 0) {
            toast("至少要有一台服务器");
            return;
        }
        if (sel == 0) {
            toast("先勾选要推送的服务器");
            return;
        }
        final long seq = state.optLong("localSeq", 0) + 1;
        boolean partial = sel < n;
        int removed = state.optJSONArray("pendingRemoved") == null
                ? 0 : state.optJSONArray("pendingRemoved").length();

        String msg = "seq  " + state.optLong("localSeq", 0) + "  →  " + seq + "\n"
                + "本次推送  " + sel + " 台" + (partial ? "（共 " + n + " 台，部分推送）" : "") + "\n"
                + (removed > 0 ? "同时删除  " + removed + " 台\n" : "")
                + "目标  " + boot.optString("repo") + "  @" + boot.optString("branch", "main") + "\n"
                + "路径  " + boot.optString("path", "dist/config.enc.json") + "\n\n"
                + (partial ? "未勾选的服务器不会被改动，也不会被电视删除。\n\n" : "")
                + (partial ? "⚠ 需要电视端 ChaiSync-tv 1.1+；旧母 APK 不认识 partial，会把未勾选项删掉。\n\n" : "")
                + "发布后 raw 有 5 分钟缓存，电视最迟 5 分钟后看到；\n"
                + "客户端检测到 seq 不前进会自动换缓存键重问一次。";

        new AlertDialog.Builder(this)
                .setTitle("发布")
                .setMessage(msg)
                .setPositiveButton("发布", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int w) {
                        doPublish();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void doPublish() {
        final ProgressDialog pd = busy("准备发布…");
        pd.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Publisher.Outcome o = Publisher.publish(state, new Publisher.Progress() {
                        @Override
                        public void step(final String message) {
                            ui.post(new Runnable() {
                                @Override
                                public void run() {
                                    pd.setMessage(message);
                                }
                            });
                        }
                    });
                    persist();
                    ui.post(new Runnable() {
                        @Override
                        public void run() {
                            dismiss(pd);
                            render();
                            log("发布成功 seq=" + o.seq + "  servers=" + o.servers
                                    + (o.partial ? "/" + o.total + " (部分)" : "")
                                    + "  明文 " + o.plainBytes + "B → 信封 "
                                    + (o.envelopeBytes / 1024f) + "KiB"
                                    + (o.firstPublish ? "  (首次)" : ""));
                            if (!o.commit.isEmpty()) {
                                log("  commit " + o.commit);
                            }
                            toast("已发布 seq=" + o.seq);
                        }
                    });
                } catch (final Throwable t) {
                    ui.post(new Runnable() {
                        @Override
                        public void run() {
                            dismiss(pd);
                            log("发布失败：" + t.getMessage());
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("发布失败")
                                    .setMessage(String.valueOf(t.getMessage()))
                                    .setPositiveButton("知道了", null)
                                    .show();
                        }
                    });
                }
            }
        }, "publish").start();
    }

    /* ------------------------------------------------------------------ */
    /* 凭据导入                                                            */
    /* ------------------------------------------------------------------ */

    private void showBootstrapDialog() {
        // 如果剪贴板里已经有 bootstrap，直接给出来，省一步
        String clip = "";
        try {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cd = cm.getPrimaryClip();
            if (cd != null && cd.getItemCount() > 0) {
                CharSequence t = cd.getItemAt(0).coerceToText(this);
                if (t != null && t.toString().trim().startsWith("{")) {
                    clip = t.toString().trim();
                }
            }
        } catch (Throwable ignored) {
        }

        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(20), dp(6), dp(20), dp(2));

        TextView tip = tv("在电脑上执行：\n"
                + "node tools/bootstrap.mjs\n"
                + "把输出整段粘贴到下面。里面含发布私钥，不要发到任何聊天群。",
                11.5f, C_DIM, false);
        tip.setLineSpacing(dp(4), 1f);
        tip.setPadding(0, 0, 0, dp(10));
        box.addView(tip);

        final EditText et = new EditText(this);
        et.setText(clip);
        et.setHint("{ \"repo\": ... }");
        et.setTextSize(11);
        et.setTypeface(Typeface.MONOSPACE);
        et.setTextColor(C_TEXT);
        et.setHintTextColor(C_FAINT);
        et.setBackground(round(0xFF20242C, 0xFF39404F, 1, 9));
        et.setPadding(dp(13), dp(11), dp(13), dp(11));
        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        et.setMinLines(7);
        et.setGravity(Gravity.TOP | Gravity.START);
        box.addView(et, new ViewGroup.LayoutParams(MP, WC));

        ScrollView sv = new ScrollView(this);
        sv.addView(box, new ViewGroup.LayoutParams(MP, WC));

        AlertDialog dlg = new AlertDialog.Builder(this)
                .setTitle("导入发布凭据")
                .setView(sv)
                .setPositiveButton("导入", null)
                .setNegativeButton("稍后", null)
                .create();
        dlg.show();
        if (dlg.getWindow() != null) {
            dlg.getWindow().setBackgroundDrawable(new ColorDrawable(C_BG));
            dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        dlg.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String raw = text(et).trim();
                if (raw.isEmpty()) {
                    toast("粘贴内容为空");
                    return;
                }
                try {
                    JSONObject boot = parseBootstrap(raw);
                    state.put("bootstrap", boot);
                    if (state.optLong("localSeq", 0) <= 0 && boot.has("seq")) {
                        state.put("localSeq", boot.optLong("seq", 0));
                    }
                    persist();
                    render();
                    dlg.dismiss();
                    log("凭据已导入：" + boot.optString("repo")
                            + " · kid " + boot.optString("kid", "k1"));
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("导入成功")
                            .setMessage("私钥自检通过：用 pubKeyHex 验签、用 master 解密，往返一致。\n\n"
                                    + "公钥指纹\n" + EnvelopeSeal.fingerprint(boot.optString("pubKeyHex")))
                            .setPositiveButton("好", null)
                            .show();
                } catch (final Throwable t) {
                    log("导入失败：" + t.getMessage());
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("导入失败")
                            .setMessage(String.valueOf(t.getMessage()))
                            .setPositiveButton("知道了", null)
                            .show();
                }
            }
        });
    }

    /**
     * 解析并<b>当场做一次往返</b>。
     *
     * <p>这一步是必要的：bootstrap 里的 signingPem 与 pubKeyHex 很容易来自不同批次
     * （比如重新 genkey 后只换了部分文件）。如果不在这里验一次，错误会一直潜伏到
     * 第一次发布——而那时已经推出去一封电视打不开的信封。
     */
    private JSONObject parseBootstrap(String raw) throws Exception {
        JSONObject o = new JSONObject(raw);
        String repo = o.optString("repo", "").trim();
        if (repo.isEmpty() || !repo.contains("/")) {
            throw new IllegalArgumentException("repo 要写成 owner/repo");
        }
        String token = o.optString("token", "").trim();
        if (token.isEmpty()) {
            throw new IllegalArgumentException("token 为空");
        }
        String masterB64 = o.optString("masterB64", "").trim();
        byte[] master = Base64.decode(masterB64, Base64.DEFAULT);
        if (master.length != 32) {
            throw new IllegalArgumentException("masterB64 解出来是 " + master.length
                    + " 字节，必须 32 字节");
        }
        String pem = o.optString("signingPem", "");
        if (!EnvelopeSeal.looksLikePem(pem)) {
            throw new IllegalArgumentException("signingPem 不像 PKCS#8 PEM（缺 BEGIN PRIVATE KEY）");
        }
        String pub = o.optString("pubKeyHex", "").trim();
        if (pub.length() < 64) {
            throw new IllegalArgumentException("pubKeyHex 太短");
        }
        String kid = o.optString("kid", "k1").trim();
        if (kid.isEmpty()) {
            kid = "k1";
        }

        PrivateKey priv = EnvelopeSeal.parsePrivatePem(pem);
        byte[] probe = "{\"probe\":true}".getBytes("UTF-8");
        JSONObject env = EnvelopeSeal.seal(probe, master, kid, 1, priv, new SecureRandom());
        byte[] back = EnvelopeSeal.openSelf(env, master, pub);
        if (!new String(back, "UTF-8").equals(new String(probe, "UTF-8"))) {
            throw new IllegalStateException("往返自检不一致");
        }

        JSONObject out = new JSONObject();
        out.put("repo", repo);
        out.put("branch", fallback(o.optString("branch", "").trim(), "main"));
        out.put("path", fallback(o.optString("path", "").trim(), "dist/config.enc.json"));
        out.put("apiBase", fallback(o.optString("apiBase", "").trim(), "https://api.github.com"));
        out.put("token", token);
        out.put("kid", kid);
        out.put("masterB64", masterB64);
        out.put("signingPem", pem);
        out.put("pubKeyHex", pub);
        out.put("rawUrl", o.optString("rawUrl", ""));
        out.put("mirrors", o.optJSONArray("mirrors") == null ? new JSONArray() : o.getJSONArray("mirrors"));
        out.put("importedAt", System.currentTimeMillis());
        return out;
    }

    private void showResetMenu() {
        new AlertDialog.Builder(this)
                .setTitle("发布凭据")
                .setItems(new String[]{"重新导入", "清空全部数据"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        if (which == 0) {
                            showBootstrapDialog();
                        } else {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("确认清空？")
                                    .setMessage("会删除私钥、PAT、所有服务器配置与本地 seq 计数。\n"
                                            + "已推送到 GitHub 的信封不受影响。")
                                    .setPositiveButton("清空", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dd, int w) {
                                            Vault.wipe(MainActivity.this);
                                            state = Vault.emptyState();
                                            render();
                                            log("已清空本地数据");
                                            showBootstrapDialog();
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        }
                    }
                })
                .show();
    }

    /* ------------------------------------------------------------------ */
    /* 小工具                                                              */
    /* ------------------------------------------------------------------ */

    private void persist() throws Exception {
        try {
            Vault.save(this, state);
        } catch (java.security.InvalidKeyException e) {
            // 最常见的两种：Keystore 要求 IV 自生成（已修），或设备密钥被系统作废。
            // 原文是英文，直接弹给用户看不出该怎么办，这里补一句可执行的指引。
            String m = String.valueOf(e.getMessage());
            if (m.contains("iv not permitted")) {
                throw new IllegalStateException(
                        "这台设备的 Keystore 要求 IV 由它自己生成（" + m + "）。"
                                + "请长按顶部状态卡 → 清空全部数据 → 重新导入 bootstrap。");
            }
            if (m.contains("Key user not authenticated") || m.contains("permanently invalidated")) {
                throw new IllegalStateException(
                        "设备密钥已失效（" + m + "）。长按顶部状态卡 → 清空全部数据 → 重新导入即可重建。");
            }
            throw e;
        }
    }

    /** 版本号从 manifest 读，避免代码里写死一份、改了忘了同步。 */
    private String versionTag() {
        try {
            return "  v" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Throwable t) {
            return "";
        }
    }

    private ProgressDialog busy(String msg) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(msg);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        return pd;
    }

    private void dismiss(ProgressDialog pd) {
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
        } catch (Throwable ignored) {
        }
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void log(String s) {
        String time = new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
        logView.append("[" + time + "] " + s + "\n");
        logScroll.post(new Runnable() {
            @Override
            public void run() {
                logScroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private int dp(float v) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v,
                getResources().getDisplayMetrics()));
    }

    private TextView tv(String s, float sp, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(sp);
        t.setTextColor(color);
        if (bold) {
            t.setTypeface(Typeface.DEFAULT_BOLD);
        }
        return t;
    }

    private TextView chip(String label, int bg, int fg, float sp, int padV, int padH) {
        TextView t = new TextView(this);
        t.setText(label);
        t.setTextSize(sp);
        t.setTextColor(fg);
        t.setGravity(Gravity.CENTER);
        t.setBackground(round(bg, 10));
        t.setPadding(padH, padV, padH, padV);
        t.setClickable(true);
        t.setFocusable(true);
        return t;
    }

    private GradientDrawable round(int color, float radiusDp) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radiusDp));
        return d;
    }

    private GradientDrawable round(int fill, int stroke, int strokeWidthDp, float radiusDp) {
        GradientDrawable d = round(fill, radiusDp);
        d.setStroke(dp(strokeWidthDp), stroke);
        return d;
    }

    private EditText field(LinearLayout parent, String label, String value, int inputType) {
        TextView l = tv(label, 10.5f, C_DIM, false);
        l.setPadding(dp(2), dp(9), 0, dp(3));
        parent.addView(l);

        EditText et = new EditText(this);
        et.setText(value);
        et.setTextSize(13.5f);
        et.setTextColor(C_TEXT);
        et.setHintTextColor(C_FAINT);
        // 系统 EditText 在深色对话框里只有一根下划线，几乎看不出输入框在哪。
        // 直接给一个带描边的圆角块，输入区域才明确。
        et.setBackground(round(0xFF20242C, 0xFF39404F, 1, 9));
        et.setPadding(dp(13), dp(11), dp(13), dp(11));
        et.setInputType(inputType);
        et.setSingleLine((inputType & InputType.TYPE_TEXT_FLAG_MULTI_LINE) == 0);
        parent.addView(et, new ViewGroup.LayoutParams(MP, WC));
        return et;
    }

    private static String text(EditText et) {
        return et.getText() == null ? "" : et.getText().toString();
    }

    private static String fallback(String v, String dflt) {
        return v == null || v.trim().isEmpty() ? dflt : v.trim();
    }

    private static void copyIfPresent(JSONObject from, JSONObject to, String key) {
        try {
            if (from.has(key) && !from.isNull(key)) {
                to.put(key, from.get(key));
            }
        } catch (Throwable ignored) {
        }
    }

    private static String ago(long ts) {
        long d = System.currentTimeMillis() - ts;
        if (d < 0) {
            return "刚刚";
        }
        long m = d / 60000L;
        if (m < 1) {
            return "刚刚";
        }
        if (m < 60) {
            return m + " 分钟前";
        }
        long h = m / 60;
        if (h < 24) {
            return h + " 小时前";
        }
        return (h / 24) + " 天前";
    }
}
