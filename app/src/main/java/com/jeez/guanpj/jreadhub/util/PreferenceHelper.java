package com.jeez.guanpj.jreadhub.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.jeez.guanpj.jreadhub.ReadhubApplication;

public class PreferenceHelper {

    private SharedPreferences mPreferences;
    private static PreferenceHelper sInstance;

    public static PreferenceHelper getInstant() {
        if (null == sInstance) {
            sInstance = new PreferenceHelper();
        }
        return sInstance;
    }

    private PreferenceHelper() {
        mPreferences = ReadhubApplication.getInstance().getSharedPreferences(Constants.KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void setTheme(String theme) {
        mPreferences.edit().putString(Constants.Key.THEME_MODE, theme).apply();
    }

    public String getTheme() {
        return mPreferences.getString(Constants.Key.THEME_MODE, Constants.ThemeType.Blue);
    }

    public void setUseSystemBrowser(boolean b) {
        mPreferences.edit().putBoolean(Constants.Key.USE_SYS_BROWSER, b).apply();
    }

    public boolean isUseSystemBrowser() {
        return mPreferences.getBoolean(Constants.Key.USE_SYS_BROWSER, false);
    }

    public void setAutoUpgrade(boolean b) {
        mPreferences.edit().putBoolean(Constants.Key.AUTO_UPGRADE, b).apply();
    }

    public boolean isAutoUpgrade() {
        return mPreferences.getBoolean(Constants.Key.AUTO_UPGRADE, false);
    }
}
