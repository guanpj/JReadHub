package com.jeez.guanpj.jreadhub.mvpframe.view;

/**
 * Created by Jie on 2016-11-2.
 */

public interface IBaseView {
    void onRequestStart();

    void onRequestError(String msg);

    void onRequestEnd();

    void onInternetError();
}
