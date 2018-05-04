package com.jeez.guanpj.jreadhub.util;

import com.jeez.guanpj.jreadhub.ReadhubApplication;

import java.io.File;

/**
 * Created by Jie on 2016-11-1.
 */

public class Constants {

    public static final String PATH_DATA = ReadhubApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String BUNDLE_TOPIC_ID = "BUNDLE_TOPIC_ID";
    public static final String BUNDLE_NEWS_TYPE = "BUNDLE_NEWS_TYPE";
    public static final String EXTRA_TOPIC = "EXTRA_TOPIC";
    public static final String EXTRA_TOPIC_URL = "EXTRA_TOPIC_URL";
    public static final String TOPIC_DETAIL_URL = "https://readhub.me/topic/";

    public interface Key {
        String THEME_MODE = "theme_mode";
    }

    public interface Theme {
        String Blue = "blue";
        String White = "white";
        String Gray = "gray";
    }

}
