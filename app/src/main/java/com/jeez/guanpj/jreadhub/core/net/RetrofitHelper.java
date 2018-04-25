package com.jeez.guanpj.jreadhub.core.net;

import com.jeez.guanpj.jreadhub.bean.old.DataListBean;
import com.jeez.guanpj.jreadhub.bean.old.NewsBean;
import com.jeez.guanpj.jreadhub.bean.old.TopicBean;
import com.jeez.guanpj.jreadhub.core.net.api.ReadhubApi;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RetrofitHelper implements NetHelper{
    private ReadhubApi readhubApi;

    @Inject
    public RetrofitHelper(ReadhubApi readhubApi) {
        this.readhubApi = readhubApi;
    }

    @Override
    public Observable<DataListBean<TopicBean>> getTopicList(Long lastCursor, int pageSize) {
        return readhubApi.getTopicList(lastCursor, pageSize);
    }

    @Override
    public Observable<DataListBean<NewsBean>> getNewsList(@NewsBean.Type String type, Long lastCursor, int pageSize) {
        return readhubApi.getNewsList(type, lastCursor, pageSize);
    }
}
