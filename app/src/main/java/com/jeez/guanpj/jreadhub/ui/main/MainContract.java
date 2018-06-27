package com.jeez.guanpj.jreadhub.ui.main;

import com.jeez.guanpj.jreadhub.event.OpenWebSiteEvent;
import com.jeez.guanpj.jreadhub.event.SetDrawerStatusEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarNavigationClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

public interface MainContract {
    interface View extends IBaseMvpView {
        void onToolbarNavigationClickEvent(ToolbarNavigationClickEvent event);

        void onSetDrawerStatusEvent(SetDrawerStatusEvent event);

        void onOpenWebSiteEvent(OpenWebSiteEvent event);

        void changeTheme();
    }

    interface Presenter extends IBasePresenter<View> {
    }
}
