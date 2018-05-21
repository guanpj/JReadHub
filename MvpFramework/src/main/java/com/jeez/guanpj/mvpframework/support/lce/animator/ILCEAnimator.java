package com.jeez.guanpj.mvpframework.support.lce.animator;

import android.view.View;

public interface ILCEAnimator {
    void showLoading(View loadingView, View contentView, View errorView);

    void showErrorView(View loadingView, View contentView, View errorView);

    void showContent(View loadingView, View contentView, View errorView);
}
