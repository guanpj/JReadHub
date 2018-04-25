package com.jeez.guanpj.jreadhub.ui.hottest;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

public interface HottestContract {
    interface View extends IBaseView {
        void onRequestEnd(DataListBean<TopicBean> data, boolean isPull2Refresh);
        void onRequestError(boolean isPull2Refresh);
    }

    interface Presenter extends IBasePresenter<View> {
        void doRefresh();

        void doLoadMore(Long lastCursor);
    }
}
