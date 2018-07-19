package com.jeez.guanpj.jreadhub.module.star.topic;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.IBaseMvpLceView;

import java.util.List;

public interface StarTopicContract {
    interface View<M> extends IBaseMvpLceView<M> {
        void onFabClick();
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 刷新数据
         */
        void doRefresh(boolean isPullToRefresh);

        /**
         * 加载更多
         * @param lastCursor
         */
        void doLoadMore(Long lastCursor);
    }
}
