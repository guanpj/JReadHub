package com.jeez.guanpj.jreadhub.mvpframe.view;

/**
 * Created by Jie on 2016-11-2.
 */

public interface IBaseView {
    void showLoading(boolean isPullToRefresh);

    void showContent();

    void showError();
}
