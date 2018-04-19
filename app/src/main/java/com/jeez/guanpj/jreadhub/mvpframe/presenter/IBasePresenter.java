package com.jeez.guanpj.jreadhub.mvpframe.presenter;

import com.jeez.guanpj.jreadhub.mvpframe.model.IBaseModel;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

public interface IBasePresenter<M extends IBaseModel, V extends IBaseView> {
    void onAttatch(M model, V view);

    void onDetach();
}
