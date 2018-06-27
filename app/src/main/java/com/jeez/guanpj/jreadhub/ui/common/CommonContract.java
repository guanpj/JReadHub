package com.jeez.guanpj.jreadhub.ui.common;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

import java.util.List;

public interface CommonContract {
    interface View extends IBaseMvpView {
        void onRequestStart();

        void onRequestEnd(DataListBean<NewsBean> data, boolean isPull2Refresh);

        void onRequestError(boolean isPull2Refresh);

        void onFabClick();

        void onDiffResult(DiffUtil.DiffResult diffResult, List<NewsBean> newData);
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

        /**
         * 对比数据差异
         * @param oldData
         * @param newData
         */
        void getDiffResult(List<NewsBean> oldData, List<NewsBean> newData);
    }
}
