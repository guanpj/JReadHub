package com.jeez.guanpj.mvpframework.support.delegate.activity;

import android.os.Bundle;

import com.jeez.guanpj.mvpframework.base.presenter.MvpPresenter;
import com.jeez.guanpj.mvpframework.base.view.MvpView;
import com.jeez.guanpj.mvpframework.support.delegate.MvpDelegateCallbackProxy;

public class ActivityMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements ActivityMvpDelegate<V, P> {

    private MvpDelegateCallbackProxy<V, P> proxy;
    private ActivityMvpDelegateCallback<V, P> activityMVPDelegateCallback;

    public ActivityMvpDelegateImpl(ActivityMvpDelegateCallback<V, P> activityMVPDelegateCallback) {
        if (activityMVPDelegateCallback == null) {
            throw new NullPointerException("delegateCallback is null!");
        }
        this.activityMVPDelegateCallback = activityMVPDelegateCallback;
    }

    private MvpDelegateCallbackProxy<V, P> getDelegateProxy() {
        if (proxy == null) {
            proxy = new MvpDelegateCallbackProxy<>(this.activityMVPDelegateCallback);
        }
        return proxy;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Object instace = activityMVPDelegateCallback.getLastCustomNonConfigurationInstance();
        if (instace == null && instace instanceof ActivityMvpConfigurationInstance) {
            ActivityMvpConfigurationInstance<V, P> configurationInstance = (ActivityMvpConfigurationInstance<V, P>) instace;
            if (configurationInstance.getPresenter() == null) {
                getDelegateProxy().createPresenter();
            } else {
                getDelegateProxy().setPresenter(configurationInstance.getPresenter());
            }
        } else {
            getDelegateProxy().createPresenter();
        }
        getDelegateProxy().attachView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        getDelegateProxy().detachView();
    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onAttachedToWindow() {

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        boolean retained = this.activityMVPDelegateCallback.shouldInstanceBeRetained();
        P presenter = retained ? activityMVPDelegateCallback.getPresenter() : null;
        Object instance = activityMVPDelegateCallback.onRetainCustomNonConfigurationInstance();
        if (presenter == null && instance == null) {
            return null;
        }
        return new ActivityMvpConfigurationInstance<>(presenter, instance);
    }

    @Override
    public Object getLastCustomNonConfigurationInstance() {
        Object instance = activityMVPDelegateCallback.getLastCustomNonConfigurationInstance();
        if (instance != null && instance instanceof ActivityMvpConfigurationInstance) {
            ActivityMvpConfigurationInstance<V,P> configurationInstance = (ActivityMvpConfigurationInstance<V,P>) instance;
            return configurationInstance.getCustomeConfigurationInstance();
        }
        return null;
    }
}
