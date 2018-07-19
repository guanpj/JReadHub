package com.jeez.guanpj.jreadhub.module.star.news;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.IBaseMvpLceView;
import com.jeez.guanpj.jreadhub.util.Constants;

import java.util.List;

public interface StarCommonContract {
    interface View<M> extends IBaseMvpLceView<M> {
        void onFabClick(int currentPageIndex);
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
