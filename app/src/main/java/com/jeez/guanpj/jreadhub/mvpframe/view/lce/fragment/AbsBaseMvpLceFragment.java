package com.jeez.guanpj.jreadhub.mvpframe.view.lce.fragment;

import android.os.Bundle;
import android.view.View;

import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.IBaseMvpLceView;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.MvpLceViewDelegate;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.animator.ILceSwitchEffect;

public abstract class AbsBaseMvpLceFragment<M, P extends BasePresenter> extends AbsBaseMvpFragment<P> implements IBaseMvpLceView<M> {

    private MvpLceViewDelegate lceViewDelegate;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (lceViewDelegate == null) {
            lceViewDelegate = new MvpLceViewDelegate();
        }
        initLceView(view);
    }

    private void initLceView(View v) {
        lceViewDelegate.initLceView(v);
        lceViewDelegate.setOnErrorViewClickListener(view -> loadData(false));
    }

    public void setLceSwitchEffect(ILceSwitchEffect lceSwitchEffect){
        lceViewDelegate.setLceSwitchEffect(lceSwitchEffect);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        lceViewDelegate.showLoading(pullToRefresh);
    }

    @Override
    public void showContent() {
        lceViewDelegate.showContent();
    }

    @Override
    public void showError() {
        lceViewDelegate.showError();
    }

    @Override
    public void showEmpty() {
        lceViewDelegate.showEmpty();
    }

    @Override
    public void bindData(M data, boolean replaceData) {
        lceViewDelegate.bindData(data, replaceData);
    }

    @Override
    public void loadData(boolean isPullToRefresh) {
        lceViewDelegate.loadData(isPullToRefresh);
    }
}
