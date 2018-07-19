package com.jeez.guanpj.jreadhub.core;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.InstantReadBean;
import com.jeez.guanpj.jreadhub.bean.NewTopicCountBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.net.NetHelper;
import com.jeez.guanpj.jreadhub.db.DatabaseHelper;
import com.jeez.guanpj.jreadhub.util.Constants;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DataManager implements NetHelper, DatabaseHelper {
    private NetHelper mNetHelper;
    private DatabaseHelper mDbHelper;

    public DataManager(NetHelper netHelper, DatabaseHelper databaseHelper) {
        this.mNetHelper = netHelper;
        this.mDbHelper = databaseHelper;
    }

    @Override
    public Observable<DataListBean<TopicBean>> getTopicList(Long lastCursor, int pageSize) {
        return mNetHelper.getTopicList(lastCursor, pageSize);
    }

    @Override
    public Observable<DataListBean<NewsBean>> getNewsList(@Constants.Type String type, Long lastCursor, int pageSize) {
        return mNetHelper.getNewsList(type, lastCursor, pageSize);
    }

    @Override
    public Observable<InstantReadBean> getTopicInstantRead(String topicId) {
        return mNetHelper.getTopicInstantRead(topicId);
    }

    @Override
    public Observable<TopicBean> getTopicDetail(String topicId) {
        return mNetHelper.getTopicDetail(topicId);
    }

    @Override
    public Observable<List<RelevantTopicBean>> getRelateTopic(String topicId, int eventType, long order, long timeStamp) {
        return mNetHelper.getRelateTopic(topicId, eventType, order, timeStamp);
    }

    @Override
    public Observable<NewTopicCountBean> getNewTopicCount(Long latestCursor) {
        return mNetHelper.getNewTopicCount(latestCursor);
    }

    @Override
    public <T> Flowable<T> get(Class<T> tClass, String id) {
        return mDbHelper.get(tClass, id);
    }

    @Override
    public Flowable<List<TopicBean>> getAllTopic() {
        return mDbHelper.getAllTopic();
    }

    @Override
    public Maybe<List<TopicBean>> getAllTopic1() {
        return mDbHelper.getAllTopic1();
    }

    @Override
    public Single<List<TopicBean>> getAllTopic2() {
        return mDbHelper.getAllTopic2();
    }

    @Override
    public Flowable<List<NewsBean>> getAllNews() {
        return mDbHelper.getAllNews();
    }

    @Override
    public void delete(Object object) {
        mDbHelper.delete(object);
    }

    @Override
    public void insert(Object object) {
        mDbHelper.insert(object);
    }
}
