package com.jeez.guanpj.jreadhub.db;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;

import java.util.List;

import io.reactivex.Flowable;

public interface DatabaseHelper {
    <T> Flowable<T> get(Class<T> tClass, String id);

    Flowable<List<TopicBean>> getAllTopic();

    Flowable<List<NewsBean>> getAllNews();

    void delete(Object object);

    void insert(Object object);
}
