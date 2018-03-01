package com.jeez.guanpj.mvpframework.support.lce;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;
import com.jeez.guanpj.mvpframework.support.MVPActivity;

public abstract class MVPLCEActivity<M, V extends MVPView, P extends MVPPresenter<V>>
        extends MVPActivity<V, P> implements MVPLCEView<M> {

    @Override
    public void showLoading(boolean isPullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError() {

    }
}
