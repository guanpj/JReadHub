package com.jeez.guanpj.jreadhub.core.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.jeez.guanpj.jreadhub.ReadhubApplication;
import com.jeez.guanpj.jreadhub.util.Constants;

import javax.inject.Inject;

public class PreferenceHelperImpl implements PreferenceHelper {

    private SharedPreferences mPreferences;

    @Inject
    PreferenceHelperImpl() {
        mPreferences = ReadhubApplication.getInstance().getSharedPreferences(Constants.KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    @Override
    public void setTheme(String theme) {
        mPreferences.edit().putString(Constants.Key.THEME_MODE, theme).apply();
    }

    @Override
    public String getTheme() {
        return mPreferences.getString(Constants.Key.THEME_MODE, Constants.ThemeType.Blue);
    }

    @Override
    public void setUseSystemBrowser(boolean b) {
        mPreferences.edit().putBoolean(Constants.Key.USE_SYS_BROWSER, b).apply();
    }

    @Override
    public boolean isUseSystemBrowser() {
        return mPreferences.getBoolean(Constants.Key.USE_SYS_BROWSER, true);
    }
}
