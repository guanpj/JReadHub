package com.jeez.guanpj.jreadhub.module.settting;

import com.jeez.guanpj.jreadhub.data.DataManager;
import com.jeez.guanpj.jreadhub.util.PreferenceHelper;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;

import javax.inject.Inject;

public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    private DataManager mDataManager;

    @Inject
    SettingPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = mDataManager;
    }

    @Override
    public void setAutoCheckUpgrade(boolean b) {
        PreferenceHelper.getInstant().setAutoUpgrade(b);
    }

    @Override
    public boolean isAutoUpgrade() {
        return PreferenceHelper.getInstant().isAutoUpgrade();
    }

    /*@Override
    public void setTheme(@Constants.Theme String theme) {
        mDataManager.setTheme(theme);
    }

    @Override
    public void setUserSystemBrowser(boolean b) {
        mDataManager.setUseSystemBrowser(b);
    }*/
}
