package com.jeez.guanpj.mvpframework.support;

import android.support.v4.app.FragmentActivity;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;
import com.jeez.guanpj.mvpframework.support.delegate.activity.ActivityMVPDelegateCallback;

public abstract class MVPActivity<V extends MVPView, P extends MVPPresenter<V>>
        extends FragmentActivity implements ActivityMVPDelegateCallback<V, P>, MVPView {
}
