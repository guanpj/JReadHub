package com.jeez.guanpj.jreadhub.module.settting;

import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

public interface SettingContract {
    interface View extends IBaseMvpView {

    }

    interface Presenter extends IBasePresenter<View> {
        /*void setTheme(@Constants.Theme String theme);

        void setUserSystemBrowser(boolean b);*/
        void setAutoCheckUpgrade(boolean b);

        boolean isAutoUpgrade();
    }
}
