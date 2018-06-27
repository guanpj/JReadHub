package com.jeez.guanpj.jreadhub.mvpframe.view.activity;

import android.os.Bundle;

import com.jeez.guanpj.jreadhub.ReadhubApplication;
import com.jeez.guanpj.jreadhub.base.activity.AbsBaseSwipeBackActivity;
import com.jeez.guanpj.jreadhub.di.component.ActivityComponent;
import com.jeez.guanpj.jreadhub.di.component.DaggerActivityComponent;
import com.jeez.guanpj.jreadhub.di.module.ActivityModule;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SwipeBackLayout;

/**
 * Created by Jie on 2016-11-2.
 */

public abstract class AbsBaseMvpSwipeBackActivity<P extends BasePresenter> extends AbsBaseSwipeBackActivity implements IBaseMvpView {

    @Inject
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performInject();
        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL);
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

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroy();
    }
}
