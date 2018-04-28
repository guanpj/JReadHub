package com.jeez.guanpj.jreadhub.ui.instant;

import com.jeez.guanpj.jreadhub.bean.InstantReadBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

public interface InstantReadContract {
    interface View extends IBaseView {
        void onRequestStart();

        void onRequestEnd(InstantReadBean data);

        void onRequestError();
    }

    interface Presenter extends IBasePresenter<View> {
        void getTopicInstantRead(String topicId);
    }
}
