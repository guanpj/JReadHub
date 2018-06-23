package com.jeez.guanpj.jreadhub.core;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.InstantReadBean;
import com.jeez.guanpj.jreadhub.bean.NewTopicCountBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.net.NetHelper;

import java.util.List;

import io.reactivex.Observable;

public class DataManager implements NetHelper {
    private NetHelper mNetHelper;

    public DataManager(NetHelper mNetHelper) {
        this.mNetHelper = mNetHelper;
    }

    @Override
    public Observable<DataListBean<TopicBean>> getTopicList(Long lastCursor, int pageSize) {
        return mNetHelper.getTopicList(lastCursor, pageSize);
    }

    @Override
    public Observable<DataListBean<NewsBean>> getNewsList(@NewsBean.Type String type, Long lastCursor, int pageSize) {
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
}
