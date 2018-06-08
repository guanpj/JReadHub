package com.jeez.guanpj.jreadhub.ui.main;

import com.jeez.guanpj.jreadhub.event.SetDrawerStatusEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarNavigationClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

public interface MainContract {
    interface View extends IBaseView {
        void onToolbarNavigationClickEvent(ToolbarNavigationClickEvent event);

        void onSetDrawerStatusEvent(SetDrawerStatusEvent event);
    }

    interface Presenter extends IBasePresenter<View> {
    }
}
