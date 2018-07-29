package com.jeez.guanpj.jreadhub.module.star.topic;

import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.IBaseMvpLceView;

public interface StarTopicContract {
    interface View<M> extends IBaseMvpLceView<M> {
        void onFabClick();
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 根据关键字获取话题
         */
        void getDataWithKeyword(String keyWord);
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
