package com.jeez.guanpj.jreadhub.ui.topic.detail.relate;

import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

public interface RelevantTopicContract {
    interface View extends IBaseView {
        void onRequestStart();

        void onRequestTopicEnd(RelevantTopicBean bean);

        void onRequestError();
    }

    interface Presenter extends IBasePresenter<View> {
        void getRelateTopic(String topicId, int eventType, long order, long timeStamp);
    }
}
