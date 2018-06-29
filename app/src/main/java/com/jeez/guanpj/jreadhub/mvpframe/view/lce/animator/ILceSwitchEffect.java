package com.jeez.guanpj.jreadhub.mvpframe.view.lce.animator;

import android.view.View;

public interface ILceSwitchEffect {
	public void showLoading(View loadingView, View contentView, View errorView);

	public void showErrorView(View loadingView, View contentView, View errorView);

	public void showContent(View loadingView, View contentView, View errorView);
}
