package com.jeez.guanpj.mvpframework.base.presenter;

import com.jeez.guanpj.mvpframework.base.view.MvpView;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView();

}
