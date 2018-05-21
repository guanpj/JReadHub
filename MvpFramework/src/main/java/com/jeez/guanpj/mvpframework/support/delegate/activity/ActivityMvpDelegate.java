package com.jeez.guanpj.mvpframework.support.delegate.activity;

import android.os.Bundle;

import com.jeez.guanpj.mvpframework.base.presenter.MvpPresenter;
import com.jeez.guanpj.mvpframework.base.view.MvpView;

public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {
    public void onCreate(Bundle savedInstanceState);

    public void onStart();

    public void onPause();

    public void onResume();

    public void onRestart();

    public void onStop();

    public void onDestroy();

    public void onContentChanged();

    public void onSaveInstanceState(Bundle outState);

    public void onAttachedToWindow();

    public Object onRetainCustomNonConfigurationInstance();

    public Object getLastCustomNonConfigurationInstance();
}
