package com.jeez.guanpj.mvpframework.support.delegate.fragment;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;
import com.jeez.guanpj.mvpframework.support.delegate.MVPDelegateCallback;

public interface FragmentMVPDelegateCallback<V extends MVPView, P extends MVPPresenter<V>>
        extends MVPDelegateCallback<V, P> {
}
