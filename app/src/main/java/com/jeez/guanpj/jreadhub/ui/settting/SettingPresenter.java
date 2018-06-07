package com.jeez.guanpj.jreadhub.ui.settting;

import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    private DataManager mDataManager;

    @Inject
    SettingPresenter(DataManager dataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void onAttatch(SettingContract.View view) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void addRxBindingSubscribe(Disposable disposable) {

    }
}
