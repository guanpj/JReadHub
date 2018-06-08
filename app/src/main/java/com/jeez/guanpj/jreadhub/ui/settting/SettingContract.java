package com.jeez.guanpj.jreadhub.ui.settting;

import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;
import com.jeez.guanpj.jreadhub.util.Constants;

public interface SettingContract {
    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter<View> {
        void setTheme(@Constants.Theme String theme);

        void setUserSystemBrowser(boolean b);
    }
}
