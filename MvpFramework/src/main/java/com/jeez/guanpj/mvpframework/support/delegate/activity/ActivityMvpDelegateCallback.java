package com.jeez.guanpj.mvpframework.support.delegate.activity;

import com.jeez.guanpj.mvpframework.base.presenter.MvpPresenter;
import com.jeez.guanpj.mvpframework.base.view.MvpView;
import com.jeez.guanpj.mvpframework.support.delegate.MvpDelegateCallback;

public interface ActivityMvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpDelegateCallback<V, P> {

    public Object getLastCustomNonConfigurationInstance();

    public Object onRetainCustomNonConfigurationInstance();

    public Object getNonLastCustomNonConfigurationInstance();
}
