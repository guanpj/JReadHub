package com.jeez.guanpj.jreadhub.util;

import android.support.annotation.StringDef;

import com.jeez.guanpj.jreadhub.ReadhubApplicationLike;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jie on 2016-11-1.
 */

public class Constants {

    public static final String READHUB_HOST = "https://readhub.me";
    public static final String CODE_SITE = "https://github.com/guanpj/JReadHub";
    public static final String PATH_DATA = ReadhubApplicationLike.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String BUNDLE_TOPIC_ID = "BUNDLE_TOPIC_ID";
    public static final String BUNDLE_NEWS_TYPE = "BUNDLE_NEWS_TYPE";
    public static final String EXTRA_TOPIC_URL = "EXTRA_TOPIC_URL";
    public static final String EXTRA_TOPIC_TITLE = "EXTRA_TOPIC_TITLE";

    public static final int TOPIC_PAGE_SIZE = 20;
    public static int TOPIC_TOP_COUNT = 0;

    public static final long EXIT_WAIT_TIME = 2000L;

    public static final String KEY_SHARED_PREFERENCE = "readhub_preference";

    public interface Key {
        String THEME_MODE = "theme_mode";
        String USE_SYS_BROWSER = "user_sys_browser";
        String AUTO_UPGRADE = "auto_upgrade";
    }

    @StringDef({ThemeType.Blue, ThemeType.Gray})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Theme {}

    public interface ThemeType {
        String Blue = "blue";
        String Gray = "gray";
    }
}
