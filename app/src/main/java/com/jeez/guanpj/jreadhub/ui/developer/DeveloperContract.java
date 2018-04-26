package com.jeez.guanpj.jreadhub.ui.developer;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

public interface DeveloperContract {
    interface View extends IBaseView {
        void onRequestEnd(DataListBean<NewsBean> data, boolean isPull2Refresh);

        void onRequestError(boolean isPull2Refresh);
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 刷新数据
         */
        void doRefresh();

        /**
         * 加载更多
         * @param lastCursor
         */
        void doLoadMore(Long lastCursor);
    }
}
