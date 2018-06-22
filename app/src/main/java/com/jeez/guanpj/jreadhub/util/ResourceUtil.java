package com.jeez.guanpj.jreadhub.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.TypedValue;

public class ResourceUtil {

    public static int getResource(@NonNull Activity activity, @AttrRes int resourceId) {
        Resources.Theme theme = activity.getTheme();
        TypedValue ta = new TypedValue();
        theme.resolveAttribute(resourceId, ta, true);
        return ta.resourceId;
    }

    @ColorInt
    public static int getThemeAttrColor(@NonNull Context context, @AttrRes int attr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{attr});
        try {
            return a.getColor(0, 0);
        } finally {
            a.recycle();
        }
    }
}
