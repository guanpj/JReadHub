package com.jeez.guanpj.jreadhub.mvpframe.view.lce.animator;

import android.view.View;

public class EmptyEffect implements ILceSwitchEffect {

    private volatile static EmptyEffect mEffect;

    public EmptyEffect() {
    }

    public static EmptyEffect getInstance() {
        if (mEffect == null) {
            synchronized (EmptyEffect.class) {
                if (mEffect == null) {
                    mEffect = new EmptyEffect();
                }
            }
        }
        return mEffect;
    }

    @Override
    public void showLoading(View loadingView, View contentView, View errorView) {
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorView(View loadingView, View contentView, View errorView) {
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showContent(View loadingView, View contentView, View errorView) {
        contentView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }
}
