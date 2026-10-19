package com.dh.myembyapp.sync;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 首页顶栏的服务器圆点。
 *
 * <p>不修改原 APK 的 Compose 代码，也不申请悬浮窗权限：直接挂到 Activity 的 decorView 上。
 * decorView 是 FrameLayout，圆点条作为最后一个子 View 盖在内容上方，
 * 随 Activity 生命周期一起销毁，不需要任何额外权限。
 *
 * <p>只显示 managed_ids 里的服务器——也就是真正推送过的那些。
 * 当前 last_used_server 用绿色标记；点一下写 prefs 再 recreate，
 * 原 App 的 MainActivity.onCreate 会重新读 last_used_server，从而完成切换。
 */
public final class HomeBar {

    private static final String TAG = "chaisync-home-bar";
    private static final String SERVER_PREFS = "server_configs";
    private static final String SYNC_PREFS = "sync_settings";
    private static final String MAIN_ACTIVITY = "com.dh.myembyapp.MainActivity";

    private static final int C_BG = 0xE60E1015;
    private static final int C_STROKE = 0x33FFFFFF;
    private static final int C_TEXT = 0xFFE9ECF3;
    private static final int C_DIM = 0xFF8790A3;
    private static final int C_ACTIVE = 0xFF3DD68C;
    private static final int C_ACTIVE_TEXT = 0xFF07130C;

    private static boolean installed;

    private HomeBar() {
    }

    public static void install(Context context) {
        if (installed) {
            return;
        }
        Context app = context.getApplicationContext();
        if (!(app instanceof Application)) {
            Probe.journal(context, "homebar: no Application context");
            return;
        }
        installed = true;
        ((Application) app).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (isMain(activity)) {
                    attach(activity);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if (isMain(activity)) {
                    detach(activity);
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (isMain(activity)) {
                    detach(activity);
                }
            }
        });
    }

    private static boolean isMain(Activity a) {
        return a != null && MAIN_ACTIVITY.equals(a.getClass().getName());
    }

    /* ------------------------------------------------------------------ */

    private static final class Model {
        final List<String> ids = new ArrayList<String>();
        final List<String> aliases = new ArrayList<String>();
        String current = "";
    }

    private static Model load(Context ctx) {
        Model m = new Model();
        try {
            SharedPreferences sp = ctx.getSharedPreferences(SERVER_PREFS, 0);
            JSONArray servers = new JSONArray(sp.getString("servers", "[]"));
            JSONArray order = new JSONArray(sp.getString("server_order", "[]"));
            JSONArray managed = new JSONArray(ctx.getSharedPreferences(SYNC_PREFS, 0)
                    .getString("managed_ids", "[]"));

            List<String> wanted = new ArrayList<String>();
            for (int i = 0; i < managed.length(); i++) {
                String id = managed.optString(i, "");
                if (!id.isEmpty() && !wanted.contains(id)) {
                    wanted.add(id);
                }
            }
            // 老配置没有 managed_ids：退化成 server_order / servers 顺序
            if (wanted.isEmpty()) {
                for (int i = 0; i < order.length(); i++) {
                    String id = order.optString(i, "");
                    if (!id.isEmpty() && !wanted.contains(id)) {
                        wanted.add(id);
                    }
                }
                if (wanted.isEmpty()) {
                    for (int i = 0; i < servers.length(); i++) {
                        JSONObject o = servers.optJSONObject(i);
                        if (o != null && !o.optString("id", "").isEmpty()) {
                            wanted.add(o.optString("id", ""));
                        }
                    }
                }
            }

            Set<String> added = new HashSet<String>();
            for (String id : wanted) {
                JSONObject o = find(servers, id);
                if (o == null) {
                    continue;
                }
                m.ids.add(id);
                m.aliases.add(label(o));
                added.add(id);
            }

            m.current = sp.getString("last_used_server", "");
            // 当前服务器不在这批推送里（例如本地新增还没发布）也要给一个绿点
            if (!m.current.isEmpty() && !added.contains(m.current)) {
                JSONObject o = find(servers, m.current);
                if (o != null) {
                    m.ids.add(m.current);
                    m.aliases.add(label(o));
                    added.add(m.current);
                }
            }
            // last_used_server 缺失/指向已删除条目时，至少让第一台是绿的
            if (!m.ids.isEmpty() && !added.contains(m.current)) {
                m.current = m.ids.get(0);
            }
        } catch (Throwable t) {
            Probe.journal(ctx, "homebar load: " + t.getClass().getSimpleName());
        }
        return m;
    }

    private static JSONObject find(JSONArray servers, String id) {
        for (int i = 0; i < servers.length(); i++) {
            JSONObject o = servers.optJSONObject(i);
            if (o != null && id.equals(o.optString("id", ""))) {
                return o;
            }
        }
        return null;
    }

    private static String label(JSONObject o) {
        String alias = o.optString("alias", "").trim();
        return alias.isEmpty() ? o.optString("host", "服务器") : alias;
    }

    /* ------------------------------------------------------------------ */

    private static void attach(Activity activity) {
        try {
            View decor = activity.getWindow().getDecorView();
            if (!(decor instanceof ViewGroup)) {
                return;
            }
            final ViewGroup root = (ViewGroup) decor;
            if (root.findViewWithTag(TAG) != null) {
                return;
            }
            final Model m = load(activity);
            if (m.ids.isEmpty()) {
                return;
            }

            final LinearLayout bar = new LinearLayout(activity);
            bar.setOrientation(LinearLayout.HORIZONTAL);
            bar.setGravity(Gravity.CENTER_VERTICAL);
            bar.setPadding(dp(activity, 9), dp(activity, 5), dp(activity, 8), dp(activity, 5));
            bar.setBackground(round(activity, C_BG, 999, C_STROKE, 1));
            bar.setTag(TAG);
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                bar.setElevation(dp(activity, 6));
            }

            TextView caption = new TextView(activity);
            caption.setText("服务器");
            caption.setTextSize(10f);
            caption.setTextColor(C_DIM);
            caption.setPadding(0, 0, dp(activity, 6), 0);
            bar.addView(caption, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView activeDot = null;
            for (int i = 0; i < m.ids.size(); i++) {
                final String id = m.ids.get(i);
                final String alias = m.aliases.get(i);
                final boolean active = id.equals(m.current);

                final TextView dot = new TextView(activity);
                dot.setText(String.valueOf(i + 1));
                dot.setTextSize(11.5f);
                dot.setGravity(Gravity.CENTER);
                dot.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
                dot.setTextColor(active ? C_ACTIVE_TEXT : C_TEXT);
                dot.setBackground(oval(activity, active, false));
                dot.setFocusable(true);
                dot.setFocusableInTouchMode(true);
                dot.setClickable(true);
                dot.setContentDescription("服务器 " + (i + 1) + "：" + alias);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        dp(activity, 27), dp(activity, 27));
                if (i > 0) {
                    lp.leftMargin = dp(activity, 4);
                }
                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchTo(activity, id, alias, m.current);
                    }
                });
                dot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        v.setBackground(oval(activity, active, hasFocus));
                    }
                });

                bar.addView(dot, lp);
                if (active) {
                    activeDot = dot;
                }
            }

            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.TOP | Gravity.END);
            flp.topMargin = topInset(activity);
            flp.rightMargin = dp(activity, 14);
            root.addView(bar, flp);

            final TextView focusTarget = activeDot;
            if (focusTarget != null && m.ids.size() > 1) {
                // 只有一台时不抢焦点；多台时把焦点放到当前圆点，遥控器按「下」回内容区。
                bar.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            focusTarget.requestFocus();
                        }
                    }
                });
            }
        } catch (Throwable t) {
            Probe.journal(activity, "homebar attach: " + t.getClass().getSimpleName()
                    + " " + t.getMessage());
        }
    }

    private static void detach(Activity activity) {
        try {
            View decor = activity.getWindow().getDecorView();
            if (decor instanceof ViewGroup) {
                View v = ((ViewGroup) decor).findViewWithTag(TAG);
                if (v != null) {
                    ((ViewGroup) decor).removeView(v);
                }
            }
        } catch (Throwable ignored) {
        }
    }

    private static void switchTo(Activity activity, String id, String alias, String current) {
        if (id.equals(current)) {
            Toast.makeText(activity, "当前已是 " + alias, Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sp = activity.getSharedPreferences(SERVER_PREFS, 0);
        boolean ok = sp.edit().putString("last_used_server", id).commit();
        Probe.journal(activity, "homebar switch -> " + alias + " (" + id + ") ok=" + ok);
        if (!ok) {
            Toast.makeText(activity, "切换失败：prefs 写入被拒", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(activity, "切换到 " + alias, Toast.LENGTH_SHORT).show();
        // 原 App 在 onCreate 里重新读 last_used_server，recreate 是最小侵入的切换方式
        activity.recreate();
    }

    /* ------------------------------------------------------------------ */

    private static GradientDrawable oval(Context c, boolean active, boolean focused) {
        GradientDrawable d = new GradientDrawable();
        d.setShape(GradientDrawable.OVAL);
        if (active) {
            d.setColor(focused ? 0xFF66F0B3 : C_ACTIVE);
            d.setStroke(dp(c, 2), 0xFFFFFFFF);
        } else {
            d.setColor(focused ? 0x66FFFFFF : 0x2BFFFFFF);
            d.setStroke(dp(c, focused ? 2 : 1), focused ? 0xFFFFFFFF : C_STROKE);
        }
        return d;
    }

    private static GradientDrawable round(Context c, int color, int radiusDp, int stroke, int strokeDp) {
        GradientDrawable d = new GradientDrawable();
        d.setShape(GradientDrawable.RECTANGLE);
        d.setColor(color);
        d.setCornerRadius(dp(c, radiusDp));
        if (strokeDp > 0) {
            d.setStroke(dp(c, strokeDp), stroke);
        }
        return d;
    }

    private static int topInset(Context c) {
        int id = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int h = id > 0 ? c.getResources().getDimensionPixelSize(id) : 0;
        return h + dp(c, 8);
    }

    private static int dp(Context c, float v) {
        return (int) (v * c.getResources().getDisplayMetrics().density + 0.5f);
    }
}
