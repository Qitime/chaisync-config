package com.dh.myembyapp.sync;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

/**
 * 凭据失效自愈失败的弹窗提示。
 *
 * <p>冷启动时若探测到 token 失效且用本地账密重登也失败，由 {@link SelfHeal}
 * 启动本 Activity，弹窗提示用户"需要更新账号"。窗口用系统 Material Dialog 主题，
 * 无依赖、独立于母壳 UI；点"知道了"关闭并返回主流程。
 */
public final class CredentialNotifyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("需要更新账号")
                    .setMessage("登录凭据已失效，自动重登失败。\n请重新输入账号密码进行更新。")
                    .setCancelable(false)
                    .setNeutralButton("知道了", (d, w) -> d.dismiss())
                    .create();
            dialog.setOnDismissListener(d -> finish());
            dialog.show();
        } catch (Throwable t) {
            finish();
        }
    }
}
