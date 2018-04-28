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

    public static final int PER_PAGE = 15;

    public static final String PHPHUB_HOST = "phphub.org";

    public static final String PHPHUB_USER_PATH = "users";

    public static final String PHPHUB_TOPIC_PATH = "topics";

    public static final String DEEP_LINK_PREFIX = "phphub://";

    public static final String MD_HTML_PREFIX = "<html><head><style type=\"text/css\">html,body{padding:4px 8px 4px 8px;font-family:'sans-serif-light';color:#303030;}h1,h2,h3,h4,h5,h6{font-family:'sans-serif-condensed';}a{color:#388E3C;text-decoration:underline;}img{height:auto;width:325px;margin:auto;}</style></head><body>";

    public static final String MD_HTML_SUFFIX = "</body></html>";

    public static final String ABOUT_PHPHUB = "https://laravel-china.org/about";

    public static final String ABOUT_ME = "https://github.com/Freelander";

    public static final String OPEN_SOURCE = "https://github.com/Freelander/Elephant";

    public static final String CONTACT_INFO = "huanggaojun13@gmail.com";

    public static final String LOGIN_HELP = "http://7xnqwn.com1.z0.glb.clouddn.com/index.html";

    public interface Key {
        String THEME_MODE = "theme_mode";
    }

    public interface Theme {
        String Blue = "blue";
        String White = "white";
        String Gray = "gray";
    }

}
