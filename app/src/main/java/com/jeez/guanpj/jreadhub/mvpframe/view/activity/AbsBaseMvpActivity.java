package com.jeez.guanpj.jreadhub.mvpframe.view.activity;

import android.os.Bundle;

import com.jeez.guanpj.jreadhub.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.base.activity.AbsBaseActivity;
import com.jeez.guanpj.jreadhub.di.component.ActivityComponent;
import com.jeez.guanpj.jreadhub.di.component.DaggerActivityComponent;
import com.jeez.guanpj.jreadhub.di.module.ActivityModule;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

import javax.inject.Inject;

/**
 * Created by Jie on 2016-11-2.
 */

public abstract class AbsBaseMvpActivity<P extends BasePresenter> extends AbsBaseActivity implements IBaseMvpView {

    @Inject
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performInject();
        if (null != mPresenter) {
            mPresenter.onAttatch(this);
        }
    }

    public ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(ReadhubApplicationLike.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract void performInject();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroy();
    }
}
