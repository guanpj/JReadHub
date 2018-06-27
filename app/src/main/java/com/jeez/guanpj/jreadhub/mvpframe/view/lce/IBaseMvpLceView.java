package com.jeez.guanpj.jreadhub.mvpframe.view.lce;

import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

public interface IBaseMvpLceView<M> extends IBaseMvpView {

    /**
     * 显示loading页面
     *
     * @param isPullToRefresh 是否下拉刷新
     */
    public void showLoading(boolean isPullToRefresh);

    /**
     * 显示ContentView
     */
    public void showContent();

    /**
     * 显示异常界面
     */
    public void showError();

    /**
     * 绑定数据
     * @param data
     */
    public void bindData(M data);
}
