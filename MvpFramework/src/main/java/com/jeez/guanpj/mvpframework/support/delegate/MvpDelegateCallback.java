package com.jeez.guanpj.mvpframework.support.delegate;

import com.jeez.guanpj.mvpframework.base.presenter.MvpPresenter;
import com.jeez.guanpj.mvpframework.base.view.MvpView;

public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {

    public P createPresenter();

    public P getPresenter();

    public void setPresenter(P presenter);

    public V getMvpView();

    public void setRetainInstance(boolean retaionInstance);

    public boolean isRetainInstance();

    public boolean shouldInstanceBeRetained();
}
