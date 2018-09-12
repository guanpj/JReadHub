package com.jeez.guanpj.jreadhub.module.topic.detail;

import com.jeez.guanpj.jreadhub.bean.TopicDetailBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.IBaseMvpLceView;

public interface TopicDetailContract {
    interface View extends IBaseMvpLceView<TopicDetailBean> {
        void onCheckStarResult(boolean isTopicExist);
    }

    interface Presenter extends IBasePresenter<View> {
        void getTopicDetail(String topicId, boolean isPullToRefresh);

        void checkStar(String topicId);

        void addStar(TopicDetailBean topicBean);

        void removeStar(TopicDetailBean topicBean);
    }
}
