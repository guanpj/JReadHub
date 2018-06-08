package com.jeez.guanpj.jreadhub.mvpframe.view.activity;

import android.os.Bundle;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.ReadhubApplication;
import com.jeez.guanpj.jreadhub.base.AbsBaseActivity;
import com.jeez.guanpj.jreadhub.di.component.ActivityComponent;
import com.jeez.guanpj.jreadhub.di.component.DaggerActivityComponent;
import com.jeez.guanpj.jreadhub.di.module.ActivityModule;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;
import com.jeez.guanpj.jreadhub.util.Constants;

import javax.inject.Inject;

/**
 * Created by Jie on 2016-11-2.
 */

public abstract class AbsBaseMvpActivity<P extends BasePresenter> extends AbsBaseActivity implements IBaseView {

    @Inject
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performInject();
        initTheme();
        if (null != mPresenter) {
            mPresenter.onAttatch(this);
        }
    }

    public ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(ReadhubApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract void performInject();

    private void initTheme() {
        String theme = mPresenter.getTheme();
        switch (theme) {
            case Constants.ThemeType.Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Constants.ThemeType.Gray:
                setTheme(R.style.GrayTheme);
                break;
            default:
                setTheme(R.style.BlueTheme);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroy();
    }
}
