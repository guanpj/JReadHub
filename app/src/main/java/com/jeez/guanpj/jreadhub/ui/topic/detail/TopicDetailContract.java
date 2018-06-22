package com.jeez.guanpj.jreadhub.ui.topic.detail;

import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

public interface TopicDetailContract {
    interface View extends IBaseView {
        void onRequestStart();

        void onRequestTopicEnd(TopicBean bean);

        void onRequestError();
    }

    interface Presenter extends IBasePresenter<View> {
        void getTopicDetail(String topicId);
    }
}
