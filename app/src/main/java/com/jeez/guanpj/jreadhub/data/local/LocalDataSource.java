package com.jeez.guanpj.jreadhub.data.local;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.SearchHistoryBean;
import com.jeez.guanpj.jreadhub.bean.TopicDetailBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface LocalDataSource {
    Flowable<List<TopicDetailBean>> getTopicById(@NonNull String id);

    Flowable<List<NewsBean>> getNewsById(@NonNull String id);

    <T>Single<T> getSingleBean(@NonNull Class<T> tClass, @NonNull String id);

    Flowable<List<TopicDetailBean>> getTopicsByKeyword(@NonNull String keyWord);

    Flowable<List<NewsBean>> getNewsByKeyword(@NonNull String keyWord);

    Flowable<List<TopicDetailBean>> getAllTopic();

    Flowable<List<NewsBean>> getAllNews();

    Flowable<List<SearchHistoryBean>> getAllSearchHistroy();

    Single<SearchHistoryBean> getSingleHistory(@NonNull String keyWord);

    Cursor getHistoryCursor(@NonNull String keyWord);

    <T>void deleteAll(@NonNull Class<T> tClass);

    void delete(@NonNull Object object);

    void insert(@NonNull Object object);

    void update(@NonNull Object object);
}
