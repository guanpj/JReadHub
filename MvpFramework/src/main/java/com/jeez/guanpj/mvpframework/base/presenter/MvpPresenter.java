package com.jeez.guanpj.mvpframework.base.presenter;

import com.jeez.guanpj.mvpframework.base.view.MVPView;

public interface MVPPresenter<V extends MVPView> {

    void attachView(V view);

    void detachView();

}
