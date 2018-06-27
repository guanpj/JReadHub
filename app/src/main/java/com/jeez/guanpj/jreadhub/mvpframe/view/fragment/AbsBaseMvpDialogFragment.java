package com.jeez.guanpj.jreadhub.mvpframe.view.fragment;

import android.os.Bundle;

import com.jeez.guanpj.jreadhub.ReadhubApplication;
import com.jeez.guanpj.jreadhub.base.fragment.AbsBaseDialogFragment;
import com.jeez.guanpj.jreadhub.di.component.DaggerFragmentComponent;
import com.jeez.guanpj.jreadhub.di.component.FragmentComponent;
import com.jeez.guanpj.jreadhub.di.module.FragmentModule;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

import javax.inject.Inject;

public abstract class AbsBaseMvpDialogFragment<P extends BasePresenter> extends AbsBaseDialogFragment implements IBaseMvpView {

    @Inject
    public P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performInject();
        if (null != mPresenter) {
            mPresenter.onAttatch(this);
        }
    }

    public FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(ReadhubApplication.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    protected abstract void performInject();

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroy();
    }
}
