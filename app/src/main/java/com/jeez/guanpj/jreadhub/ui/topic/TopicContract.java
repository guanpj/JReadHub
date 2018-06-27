package com.jeez.guanpj.jreadhub.ui.topic;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

import java.util.List;

public interface TopicContract {
    interface View extends IBaseMvpView {
        void onRequestStart();

        void onRequestEnd(DataListBean<TopicBean> data, boolean isPull2Refresh);

        void onRequestError(boolean isPull2Refresh);

        void onFabClick();

        void showNewTopicCount(int newTopicCount);

        void onDiffResult(DiffUtil.DiffResult diffResult, List<TopicBean> newData);
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

        /**
         * 获取新话题数量
         * @param latestCursor
         */
        void getNewTopicCount(Long latestCursor);

        /**
         * 对比数据差异
         * @param oldData
         * @param newData
         */
        void getDiffResult(List<TopicBean> oldData, List<TopicBean> newData);
    }
}
