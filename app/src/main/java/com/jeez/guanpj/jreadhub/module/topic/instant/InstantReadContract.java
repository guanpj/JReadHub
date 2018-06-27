package com.jeez.guanpj.jreadhub.module.topic.instant;

import com.jeez.guanpj.jreadhub.bean.InstantReadBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

public interface InstantReadContract {
    interface View extends IBaseMvpView {
        void onRequestStart();

        void onRequestEnd(InstantReadBean data);

        void onRequestError();
    }

    interface Presenter extends IBasePresenter<View> {
        void getTopicInstantRead(String topicId);
    }
}
