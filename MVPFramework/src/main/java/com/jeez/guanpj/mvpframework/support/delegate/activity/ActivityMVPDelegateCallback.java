package com.jeez.guanpj.mvpframework.support.delegate.activity;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;
import com.jeez.guanpj.mvpframework.support.delegate.MVPDelegateCallback;

public interface ActivityMVPDelegateCallback<V extends MVPView, P extends MVPPresenter<V>>
        extends MVPDelegateCallback<V, P> {

    public Object getLastCustomNonConfigurationInstance();

    public Object onRetainCustomNonConfigurationInstance();

    public Object getNonLastCustomNonConfigurationInstance();
}
