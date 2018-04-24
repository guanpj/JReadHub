package com.jeez.guanpj.jreadhub.base;

/**
 * Created by Jie on 2016-10-26.
 */

public interface IBaseViewFlow {
    /**
     * 获取布局Id
     */
    int getLayoutId();

    /**
     * 初始化UI控件
     */
    void initView();

    /**
     * 初始化数据和事件
     */
    void initDataAndEvent();
}
