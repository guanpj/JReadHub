package com.jeez.guanpj.mvpframework.support.delegate.activity;

import android.os.Bundle;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;

public interface ActivityMVPDelegate<V extends MVPView, P extends MVPPresenter<V>> {
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
