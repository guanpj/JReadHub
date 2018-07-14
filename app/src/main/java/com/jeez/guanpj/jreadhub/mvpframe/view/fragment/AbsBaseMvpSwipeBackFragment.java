package com.jeez.guanpj.jreadhub.mvpframe.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jeez.guanpj.jreadhub.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.base.fragment.AbsBaseSwipeBackFragment;
import com.jeez.guanpj.jreadhub.di.component.DaggerFragmentComponent;
import com.jeez.guanpj.jreadhub.di.component.FragmentComponent;
import com.jeez.guanpj.jreadhub.di.module.FragmentModule;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

import javax.inject.Inject;

public abstract class AbsBaseMvpSwipeBackFragment<P extends BasePresenter> extends AbsBaseSwipeBackFragment implements IBaseMvpView {

    @Inject
    public P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performInject();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != mPresenter) {
            mPresenter.onAttatch(this);
        }
    }

    public FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(ReadhubApplicationLike.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    protected abstract void performInject();

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroyView();
    }
}
