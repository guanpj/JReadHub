package com.jeez.guanpj.jreadhub.module.common;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.IBaseMvpLceView;

import java.util.List;

public interface CommonContract {
    interface View<M> extends IBaseMvpLceView<M> {
        void onFabClick(int currentPageIndex);

        void onDiffResult(DiffUtil.DiffResult diffResult, List<NewsBean> newData);
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 刷新数据
         */
        void doRefresh(@NewsBean.Type String type, boolean isPullToRefresh);

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
