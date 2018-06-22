package com.jeez.guanpj.jreadhub.util;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;

import com.jeez.guanpj.jreadhub.R;

public class NavigationUtil {

    public static void openInBrowser(@NonNull Activity context, @NonNull String url) {
        //过滤不规则网站防止出现异常
        String validedUrl = RegularUtil.matchWebSite(url);
        if (!TextUtils.isEmpty(validedUrl)) {
            new CustomTabsIntent.Builder()
                    .setToolbarColor(ResourceUtil.getThemeAttrColor(context, R.attr.readhubStatus))
                    .build()
                    .launchUrl(context, Uri.parse(validedUrl));
        }
    }

    public static void openByApp(@NonNull Activity activity, @NonNull String url) {
        Intent openIntent = new Intent(Intent.ACTION_VIEW);
        openIntent.setData(Uri.parse(url));
        activity.startActivity(Intent.createChooser(openIntent, "选择应用打开"));
    }

    public static void shareToApp(@NonNull Activity activity, @NonNull String content) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content + "\n——来自 Readhub 客户端");
        activity.startActivity(Intent.createChooser(shareIntent, "分享"));
    }
}
