package com.jeez.guanpj.jreadhub.moduls.test.view;

import android.support.annotation.NonNull;
import android.view.View;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.moduls.test.widget.LoadingView;
import com.jeez.guanpj.mvpframework.support.lce.animator.DefaultLceAnimator;

public class LoadingAnimator extends DefaultLceAnimator {

    private LoadingView loading;

    @Override
    public void showLoading(View loadingView, View contentView, View errorView) {
        super.showLoading(loadingView, contentView, errorView);
        loading = (LoadingView) loadingView.findViewById(R.id.lv_loading);
        loading.openAnimation();
    }

    @Override
    public void showContent(View loadingView, View contentView, @NonNull View errorView) {
        if (loading != null){
            loading.closeAnimation();
        }
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorView(@NonNull View loadingView, @NonNull View contentView, View errorView) {
        super.showErrorView(loadingView, contentView, errorView);
        if (loading != null){
            loading.closeAnimation();
        }
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }
}
