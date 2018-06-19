package com.jeez.guanpj.jreadhub.core.preference;

public interface IPreferenceHelper {
    void setTheme(String theme);

    String getTheme();

    void setUseSystemBrowser(boolean b);

    boolean isUseSystemBrowser();

    void setAutoUpgrade(boolean b);

    boolean isAutoUpgrade();
}
