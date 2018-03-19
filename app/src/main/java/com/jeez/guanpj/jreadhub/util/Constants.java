package com.jeez.guanpj.jreadhub.util;

/**
 * Created by Jie on 2016-11-1.
 */

public class Constants {

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
        String TOPIC = "topic";
        String TOPIC_TYPE = "topic_type";
        String USER_DATA = "user_data";
        String IS_LOGIN = "is_login";
        String COMMENT_URL = "comment_url";
        String WEB_URL = "web_url";
        String WEB_TITLE = "web_title";
        String TOPIC_IMAGE_URL = "topic_image_url";
        String TOKEN = "token";
        String TOPIC_ID = "topic_id";
        String USER_ID = "user_id";
        String PREVIEW_TOPIC_TITLE = "preview_topic_title";
        String PREVIEW_TOPIC_CONTENT = "preview_topic_content";
        String THEME_MODE = "theme_mode";
    }

    public interface Token {
        String AUTH_TYPE_GUEST = "client_credentials";
        String AUTH_TYPE_USER = "login_token";
        String AUTH_TYPE_REFRESH = "refresh_token";
    }

    public interface Topic {
        String RECOMMEND = "excellent";//精华
        String VOTE = "vote";//投票
        String NEWEST = "newest";//最新
        String NOBODY = "nobody";//零回复
        String JOBS = "jobs";//热门招聘
        String WIKI = "wiki";//社区WIKI
    }

    public interface User {
        int USER_TOPIC_FOLLOW = 1;
        int USER_TOPIC_VOTES = 2; //赞过话题
        int USER_TOPIC_MY = 3; //
    }

    public interface TopicOpt {
        int TOPIC_VOTE_UP = 5; //赞同话题
        int TOPIC_VOTE_DOWN = 6;//反对话题
    }

    public interface Activity {
        int LoginActivity = 1000;
        int UserInfoEditActivity = 1001;
    }

    public interface Theme {
        String Blue = "blue";
        String White = "white";
        String Gray = "gray";
    }

}
