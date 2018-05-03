package com.jeez.guanpj.jreadhub.ui.topic.detail;

import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicTraceBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

import java.util.List;

public interface TopicDetailContract {
    interface View extends IBaseView {
        void onRequestStart();

        void onRequestTopicEnd(TopicBean bean);

        void onRequestTopicTraceEnd(List<TopicTraceBean> beans);

        void onRequestError();
    }

    interface Presenter extends IBasePresenter<View> {
        void getTopicDetail(String topicId);
    }
}
