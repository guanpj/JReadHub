package com.jeez.guanpj.jreadhub.module.web;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

public interface WebViewContract {
    interface View extends IBaseMvpView {
        void onCheckStarResult(boolean isNewsExist);
    }

    interface Presenter {
        void checkStar(String newsId);

        void addStar(NewsBean newsBean);

        void removeStar(NewsBean newsBean);
    }
}
