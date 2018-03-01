package com.jeez.guanpj.mvpframework.support.lce;

import com.jeez.guanpj.mvpframework.base.view.MVPView;

public interface MVPLCEView<M> extends MVPView {
    void showLoading(boolean isPullToRefresh);

    void showContent();

    void showError();

    void bindData(M data);

    void loadData(boolean isPullToRefresh);
}
