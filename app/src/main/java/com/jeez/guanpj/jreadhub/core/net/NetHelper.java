package com.jeez.guanpj.jreadhub.core.net;


import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.InstantReadBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;

import java.util.List;

import io.reactivex.Observable;

public interface NetHelper {
    Observable<DataListBean<TopicBean>> getTopicList(Long lastCursor, int pageSize);

    Observable<DataListBean<NewsBean>> getNewsList(@NewsBean.Type String type, Long lastCursor, int pageSize);

    Observable<InstantReadBean> getTopicInstantRead(String topicId);

    Observable<TopicBean> getTopicDetail(String topicId);

    Observable<List<RelevantTopicBean>> getRelateTopic(String topicId, int eventType, long order, long timeStamp);
}
