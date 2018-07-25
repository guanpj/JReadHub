package com.jeez.guanpj.jreadhub.module.topic.detail;

import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.IBaseMvpLceView;

public interface TopicDetailContract {
    interface View extends IBaseMvpLceView<TopicBean> {
        void onCheckStarResult(boolean isTopicExist);
    }

    interface Presenter extends IBasePresenter<View> {
        void getTopicDetail(String topicId, boolean isPullToRefresh);

        void checkStar(String topicId);

        void addStar(TopicBean topicBean);

        void removeStar(TopicBean topicBean);
    }
}
