package com.jeez.guanpj.jreadhub.mvpframe.presenter;

import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;
import com.jeez.guanpj.jreadhub.util.Constants;

import io.reactivex.disposables.Disposable;

public interface IBasePresenter<V extends IBaseMvpView> {
    void onAttatch(V view);

    void onDetach();

    void addSubscribe(Disposable disposable);

    @Constants.Theme String getTheme();

    void setTheme(@Constants.Theme String theme);

    boolean isUseSystemBrowser();

    void setUseSystemBrowser(boolean b);
}
