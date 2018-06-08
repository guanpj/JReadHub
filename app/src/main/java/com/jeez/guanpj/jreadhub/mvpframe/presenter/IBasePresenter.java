package com.jeez.guanpj.jreadhub.mvpframe.presenter;

import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

import io.reactivex.disposables.Disposable;

public interface IBasePresenter<V extends IBaseView> {
    void onAttatch(V view);

    void onDetach();

    void addSubscribe(Disposable disposable);
}
