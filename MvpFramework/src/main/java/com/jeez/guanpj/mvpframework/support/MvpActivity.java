package com.jeez.guanpj.mvpframework.support;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jeez.guanpj.mvpframework.base.presenter.MvpPresenter;
import com.jeez.guanpj.mvpframework.base.view.MvpView;
import com.jeez.guanpj.mvpframework.support.delegate.activity.ActivityMvpDelegate;
import com.jeez.guanpj.mvpframework.support.delegate.activity.ActivityMvpDelegateCallback;
import com.jeez.guanpj.mvpframework.support.delegate.activity.ActivityMvpDelegateImpl;

public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends FragmentActivity implements ActivityMvpDelegateCallback<V, P>, MvpView {

    private P presenter;
    private ActivityMvpDelegateImpl<V, P> activityMVPDelegateImpl;
    private boolean retainInstance;

    protected ActivityMvpDelegate<V, P> getActivityMvpDelegate() {
        if (this.activityMVPDelegateImpl == null) {
            this.activityMVPDelegateImpl = new ActivityMvpDelegateImpl<>(this);
        }
        return this.activityMVPDelegateImpl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public P getPresenter() {
        return this.presenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getActivityMvpDelegate().onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getActivityMvpDelegate().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActivityMvpDelegate().onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getActivityMvpDelegate().onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getActivityMvpDelegate().onStop();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getActivityMvpDelegate().onContentChanged();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getActivityMvpDelegate().onAttachedToWindow();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getActivityMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getActivityMvpDelegate().onDestroy();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return getActivityMvpDelegate().onRetainCustomNonConfigurationInstance();
    }

    @Override
    public Object getLastCustomNonConfigurationInstance() {
        return super.getLastCustomNonConfigurationInstance();
    }

    @Override
    public Object getNonLastCustomNonConfigurationInstance() {
        return getActivityMvpDelegate().getLastCustomNonConfigurationInstance();
    }

    @Override
    public boolean isRetainInstance() {
        return retainInstance;
    }

    @Override
    public void setRetainInstance(boolean retaionInstance) {
        this.retainInstance = retaionInstance;
    }

    @Override
    public boolean shouldInstanceBeRetained() {
        return this.retainInstance && isChangingConfigurations();
    }
}
