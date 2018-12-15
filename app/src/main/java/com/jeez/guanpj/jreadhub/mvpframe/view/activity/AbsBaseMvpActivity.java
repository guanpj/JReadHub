package com.jeez.guanpj.jreadhub.mvpframe.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jeez.guanpj.jreadhub.app.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.base.activity.AbsBaseActivity;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by Jie on 2016-11-2.
 */

public abstract class AbsBaseMvpActivity<P extends BasePresenter> extends AbsBaseActivity implements IBaseMvpView, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;
    @Inject
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AndroidInjection.inject(this);
        ReadhubApplicationLike.getInstance().activityInjector()
                .inject(this);
        super.onCreate(savedInstanceState);
        if (null != mPresenter) {
            mPresenter.onAttach(this);
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroy();
    }
}
