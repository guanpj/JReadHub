package com.jeez.guanpj.mvpframework.support.delegate.fragment;

import com.jeez.guanpj.mvpframework.base.presenter.MvpPresenter;
import com.jeez.guanpj.mvpframework.base.view.MvpView;
import com.jeez.guanpj.mvpframework.support.delegate.MvpDelegateCallback;

public interface FragmentMVPDelegateCallback<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpDelegateCallback<V, P> {
}
