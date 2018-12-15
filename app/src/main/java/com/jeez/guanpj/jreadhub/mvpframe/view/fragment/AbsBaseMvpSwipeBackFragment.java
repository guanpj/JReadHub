package com.jeez.guanpj.jreadhub.mvpframe.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jeez.guanpj.jreadhub.base.fragment.AbsBaseSwipeBackFragment;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public abstract class AbsBaseMvpSwipeBackFragment<P extends BasePresenter> extends AbsBaseSwipeBackFragment implements IBaseMvpView {

    @Inject
    public P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != mPresenter) {
            mPresenter.onAttach(this);
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroyView();
    }
}
