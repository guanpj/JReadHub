package com.jeez.guanpj.jreadhub.core.net.api;

import android.os.Build;

import com.jeez.guanpj.jreadhub.BuildConfig;
import com.jeez.guanpj.jreadhub.bean.old.DataListBean;
import com.jeez.guanpj.jreadhub.bean.old.NewsBean;
import com.jeez.guanpj.jreadhub.bean.old.TopicBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReadhubApi {
    public static final String HOST = "https://api.readhub.me/";
    public static final String USER_AGENT = "Readhub-Material/" + BuildConfig.VERSION_NAME + " (Android " + Build.VERSION.RELEASE + "; " + Build.MODEL + "; " + Build.MANUFACTURER + ")";

    @GET("topic")
    Observable<DataListBean<TopicBean>> getTopicList(
            @Query("lastCursor") Long lastCursor,
            @Query("pageSize") int pageSize
    );

    @GET("{type}")
    Observable<DataListBean<NewsBean>> getNewsList(
            @Path("type") @NewsBean.Type String type,
            @Query("lastCursor") Long lastCursor,
            @Query("pageSize") int pageSize
    );
}
