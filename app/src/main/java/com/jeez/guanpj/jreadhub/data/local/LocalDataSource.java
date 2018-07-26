package com.jeez.guanpj.jreadhub.data.local;

import android.support.annotation.NonNull;

import com.jeez.guanpj.jreadhub.bean.BaseListItemBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface LocalDataSource {
    Flowable<List<TopicBean>> getTopicById(@NonNull String id);

    Flowable<List<NewsBean>> getNewsById(@NonNull String id);

    <T> Single<T> getSingleBean(@NonNull Class<T> tClass, @NonNull String id);

    Flowable<List<TopicBean>> getTopicsByKeyword(@NonNull String keyWord);

    Flowable<List<NewsBean>> getNewsByKeyword(@NonNull String keyWord);

    Flowable<List<TopicBean>> getAllTopic();

    Flowable<List<NewsBean>> getAllNews();

    void delete(@NonNull BaseListItemBean object);

    void insert(@NonNull BaseListItemBean object);

    void update(@NonNull BaseListItemBean object);
}
