package com.jeez.guanpj.jreadhub.moduls.base.presenter;

import android.content.Context;

import com.jeez.guanpj.mvpframework.base.presenter.MVPBasePresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;

public class BasePresenter<V extends MVPView> extends MVPBasePresenter<V> {
    public BasePresenter(Context context) {
        super(context);
    }
}
