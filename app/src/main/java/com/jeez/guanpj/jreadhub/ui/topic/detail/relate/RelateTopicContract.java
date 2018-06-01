package com.jeez.guanpj.jreadhub.ui.topic.detail.relate;

import com.jeez.guanpj.jreadhub.bean.RelateTopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

public interface RelateTopicContract {
    interface View extends IBaseView {
        void onRequestStart();

        void onRequestTopicEnd(RelateTopicBean bean);

        void onRequestError();
    }

    interface Presenter extends IBasePresenter<View> {
        void getRelateTopic(String topicId, int eventType, long order, long timeStamp);
    }
}
