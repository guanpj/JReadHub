package com.jeez.guanpj.jreadhub.core.net;

import com.jeez.guanpj.jreadhub.bean.old.DataListBean;
import com.jeez.guanpj.jreadhub.bean.old.NewsBean;
import com.jeez.guanpj.jreadhub.bean.old.TopicBean;

import io.reactivex.Observable;

public interface NetHelper {
    Observable<DataListBean<TopicBean>> getTopicList(Long lastCursor, int pageSize);

    Observable<DataListBean<NewsBean>> getNewsList(@NewsBean.Type String type, Long lastCursor, int pageSize);
}
