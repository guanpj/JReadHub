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
import com.jeez.guanpj.jreadhub.data.local.LocalDataSource;
import com.jeez.guanpj.jreadhub.data.remote.RemoteDataSource;
import com.jeez.guanpj.jreadhub.util.Constants;

import java.util.List;
import java.util.function.Consumer;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DataManager implements RemoteDataSource, LocalDataSource {
    private RemoteDataSource mReemoteDataSource;
    private LocalDataSource mLocalDataSource;

    public DataManager(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.mReemoteDataSource = remoteDataSource;
        this.mLocalDataSource = localDataSource;
    }

    @Override
    public Observable<DataListBean<TopicBean>> getTopicList(Long lastCursor, int pageSize) {
        return mReemoteDataSource.getTopicList(lastCursor, pageSize);
    }

    @Override
    public Observable<DataListBean<NewsBean>> getNewsList(@Constants.Type String type, Long lastCursor, int pageSize) {
        return mReemoteDataSource.getNewsList(type, lastCursor, pageSize);
    }

    @Override
    public Observable<InstantReadBean> getTopicInstantRead(String topicId) {
        return mReemoteDataSource.getTopicInstantRead(topicId);
    }

    @Override
    public Observable<TopicBean> getTopicDetail(String topicId) {
        return mReemoteDataSource.getTopicDetail(topicId);
    }

    @Override
    public Observable<List<RelevantTopicBean>> getRelateTopic(String topicId, int eventType, long order, long timeStamp) {
        return mReemoteDataSource.getRelateTopic(topicId, eventType, order, timeStamp);
    }

    @Override
    public Observable<NewTopicCountBean> getNewTopicCount(Long latestCursor) {
        return mReemoteDataSource.getNewTopicCount(latestCursor);
    }

    @Override
    public Flowable<List<TopicBean>> getTopicById(String id) {
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
    public Flowable<List<TopicBean>> getTopicsByKeyword(@NonNull String keyWord) {
        return mLocalDataSource.getTopicsByKeyword(keyWord);
    }

    @Override
    public Flowable<List<NewsBean>> getNewsByKeyword(@NonNull String keyWord) {
        return mLocalDataSource.getNewsByKeyword(keyWord);
    }

    @Override
    public Flowable<List<TopicBean>> getAllTopic() {
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
    public void getHistoryCursor(@NonNull String keyWord, @NonNull Consumer<Cursor> consumer) {
        mLocalDataSource.getHistoryCursor(keyWord, consumer);
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
