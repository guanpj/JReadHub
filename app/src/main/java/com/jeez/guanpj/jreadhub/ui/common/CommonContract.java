package com.jeez.guanpj.jreadhub.ui.common;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

public interface CommonContract {
    interface View extends IBaseView {
        void onRequestStart();

        void onRequestEnd(DataListBean<NewsBean> data, boolean isPull2Refresh);

        void onRequestError(boolean isPull2Refresh);

        void onFabClick();
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 刷新数据
         */
        void doRefresh(@NewsBean.Type String type);

        /**
         * 加载更多
         * @param lastCursor
         */
        void doLoadMore(@NewsBean.Type String type, Long lastCursor);
    }
}
