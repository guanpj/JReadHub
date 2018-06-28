package com.jeez.guanpj.jreadhub.mvpframe.view.lce;

import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

public interface IBaseMvpLceView<M> extends IBaseMvpView {

    /**
     * 显示加载中页面
     */
    public void showLoading(boolean isPullToRefresh);

    /**
     * 显示内容页面
     */
    public void showContent();

    /**
     * 显示异常界面
     */
    public void showError();

    /**
     * 绑定数据
     */
    public void bindData(M data);
}
