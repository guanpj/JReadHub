package com.jeez.guanpj.jreadhub.ui.hottest;

import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.AbsBasePresenter;

import javax.inject.Inject;

public class HottestPresenter extends AbsBasePresenter<HottestFragment> {
    private DataManager mDataManager;

    @Inject
    HottestPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }
}
