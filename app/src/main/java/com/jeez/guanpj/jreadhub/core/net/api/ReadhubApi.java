package com.jeez.guanpj.jreadhub.core.net.api;

import android.os.Build;

import com.jeez.guanpj.jreadhub.BuildConfig;
import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.InstantReadBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;

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

    @GET("/topic/instantview")
    Observable<InstantReadBean> getTopicInstantRead(@Query("topicId") String topicId);

    @GET("topic/{topic_id}")
    Observable<TopicBean> getTopicDetail(@Path("topic_id") String topicId);
}
