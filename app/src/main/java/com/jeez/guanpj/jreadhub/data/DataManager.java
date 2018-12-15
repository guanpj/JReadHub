package com.jeez.guanpj.jreadhub.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.InstantReadBean;
import com.jeez.guanpj.jreadhub.bean.NewTopicCountBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.bean.SearchHistoryBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicDetailBean;
import com.jeez.guanpj.jreadhub.data.local.LocalDataSource;
import com.jeez.guanpj.jreadhub.data.remote.RemoteDataSource;
import com.jeez.guanpj.jreadhub.util.Constants;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DataManager implements RemoteDataSource, LocalDataSource {
    private RemoteDataSource mRemoteDataSource;
    private LocalDataSource mLocalDataSource;

    public DataManager(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.mRemoteDataSource = remoteDataSource;
        this.mLocalDataSource = localDataSource;
    }

    @Override
    public Observable<DataListBean<TopicBean>> getTopicList(Long lastCursor, int pageSize) {
        return mRemoteDataSource.getTopicList(lastCursor, pageSize);
    }

    @Override
    public Observable<DataListBean<NewsBean>> getNewsList(@Constants.Type String type, Long lastCursor, int pageSize) {
        return mRemoteDataSource.getNewsList(type, lastCursor, pageSize);
    }

    @Override
    public Observable<InstantReadBean> getTopicInstantRead(String topicId) {
        return mRemoteDataSource.getTopicInstantRead(topicId);
    }

    @Override
    public Observable<TopicDetailBean> getTopicDetail(String topicId) {
        return mRemoteDataSource.getTopicDetail(topicId);
    }

    @Override
    public Observable<List<RelevantTopicBean>> getRelateTopic(String topicId, int eventType, long order, long timeStamp) {
        return mRemoteDataSource.getRelateTopic(topicId, eventType, order, timeStamp);
    }

    @Override
    public Observable<NewTopicCountBean> getNewTopicCount(Long latestCursor) {
        return mRemoteDataSource.getNewTopicCount(latestCursor);
    }

    @Override
    public Flowable<List<TopicDetailBean>> getTopicById(String id) {
        return mLocalDataSource.getTopicById(id);
    }

    @Override
    public Flowable<List<NewsBean>> getNewsById(String id) {
        return mLocalDataSource.getNewsById(id);
    }

    @Override
    public <T> Single<T> getSingleBean(Class<T> tClass, String id) {
        return mLocalDataSource.getSingleBean(tClass, id);
    }

    @Override
    public Flowable<List<TopicDetailBean>> getTopicsByKeyword(@NonNull String keyWord) {
        return mLocalDataSource.getTopicsByKeyword(keyWord);
    }

    @Override
    public Flowable<List<NewsBean>> getNewsByKeyword(@NonNull String keyWord) {
        return mLocalDataSource.getNewsByKeyword(keyWord);
    }

    @Override
    public Flowable<List<TopicDetailBean>> getAllTopic() {
        return mLocalDataSource.getAllTopic();
    }

    @Override
    public Flowable<List<NewsBean>> getAllNews() {
        return mLocalDataSource.getAllNews();
    }

    @Override
    public Flowable<List<SearchHistoryBean>> getAllSearchHistroy() {
        return mLocalDataSource.getAllSearchHistroy();
    }

    @Override
    public Single<SearchHistoryBean> getSingleHistory(@NonNull String keyWord) {
        return mLocalDataSource.getSingleHistory(keyWord);
    }

    @Override
    public Cursor getHistoryCursor(@NonNull String keyWord) {
        return mLocalDataSource.getHistoryCursor(keyWord);
    }

    @Override
    public <T> void deleteAll(Class<T> tClass) {
        mLocalDataSource.deleteAll(tClass);
    }

    @Override
    public void delete(@NonNull Object object) {
        mLocalDataSource.delete(object);
    }

    @Override
    public void insert(@NonNull Object object) {
        mLocalDataSource.insert(object);
    }

    @Override
    public void update(@NonNull Object object) {
        mLocalDataSource.update(object);
    }
}
