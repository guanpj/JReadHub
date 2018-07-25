package com.jeez.guanpj.jreadhub.core.db;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface DatabaseHelper {
    Flowable<List<TopicBean>> getTopicById(String id);

    Flowable<List<NewsBean>> getNewsById(String id);

    <T> Single<T> getSingleBean(Class<T> tClass, String id);

    Flowable<List<TopicBean>> getAllTopic();

    Flowable<List<NewsBean>> getAllNews();

    void delete(Object object);

    void insert(Object object);
}
