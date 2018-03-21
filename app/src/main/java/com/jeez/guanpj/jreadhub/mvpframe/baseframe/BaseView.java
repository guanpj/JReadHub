package com.jeez.guanpj.jreadhub.mvpframe.baseframe;

/**
 * Created by Jie on 2016-11-2.
 */

public interface BaseView {
    void onRequestStart();

    void onRequestError(String msg);

    void onRequestEnd();

    void onInternetError();
}
