package com.jeez.guanpj.jreadhub.module.topic.detail.relate;

import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

import java.util.List;

public interface RelevantTopicContract {
    interface View extends IBaseMvpView {
        void onRequestStart();

        void onRequestTopicEnd(List<RelevantTopicBean> bean);

        void onRequestError();
    }

    interface Presenter extends IBasePresenter<View> {
        void getRelateTopic(String topicId, int eventType, long order, long timeStamp);
    }
}
