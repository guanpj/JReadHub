package com.jeez.guanpj.jreadhub.mvpframe.baseframe;

import android.content.Context;

import com.jeez.guanpj.jreadhub.mvpframe.rx.RxManager;

/**
 * Created by Jie on 2016-11-2.
 */

public abstract class BasePresenter<M, V> {
    public Context context;
    public M mModel;
    public V mView;
    public RxManager mRxManager = new RxManager();

    public void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
    }

    public void onDestroy() {
        mRxManager.clear();
    }
}
