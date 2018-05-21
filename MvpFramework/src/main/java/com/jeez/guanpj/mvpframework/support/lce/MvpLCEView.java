package com.jeez.guanpj.mvpframework.support.lce;

import com.jeez.guanpj.mvpframework.base.view.MvpView;

public interface MvpLCEView<M> extends MvpView {
    void showLoading(boolean isPullToRefresh);

    void showContent();

    void showError();

    void bindData(M data);

    void loadData(boolean isPullToRefresh);
}
