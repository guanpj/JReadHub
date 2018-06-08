package com.jeez.guanpj.jreadhub.core.preference;

public interface PreferenceHelper {
    void setTheme(String theme);

    String getTheme();

    void setUseSystemBrowser(boolean b);

    boolean isUseSystemBrowser();
}
