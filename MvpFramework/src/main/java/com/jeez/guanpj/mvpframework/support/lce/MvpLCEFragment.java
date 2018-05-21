package com.jeez.guanpj.mvpframework.support.lce;

import android.os.Bundle;
import android.view.View;

import com.jeez.guanpj.mvpframework.base.presenter.MvpPresenter;
import com.jeez.guanpj.mvpframework.base.view.MvpView;
import com.jeez.guanpj.mvpframework.support.MvpFragment;
import com.jeez.guanpj.mvpframework.support.lce.animator.ILCEAnimator;

public abstract class MvpLCEFragment<M, V extends MvpView, P extends MvpPresenter<V>>
        extends MvpFragment<V, P> implements MvpLCEView<M> {
    private MvpLCEViewImpl<M> mvplceViewImpl;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mvplceViewImpl == null) {
            mvplceViewImpl = new MvpLCEViewImpl<>();
        }
        initLCEView(view);
    }

    private void initLCEView(View v) {
        mvplceViewImpl.initLceView(v);
        mvplceViewImpl.setOnErrorViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onErrorClick();
            }
        });
    }

    public void setLceAnimator(ILCEAnimator lceAnimator){
        mvplceViewImpl.setLceAnimator(lceAnimator);
    }

    @Override
    public void showLoading(boolean isPullToRefresh) {
        mvplceViewImpl.showLoading(isPullToRefresh);
    }

    @Override
    public void showContent() {
        mvplceViewImpl.showContent();
    }

    @Override
    public void showError() {
        mvplceViewImpl.showError();
    }

    @Override
    public void loadData(boolean isPullToRefresh) {
        mvplceViewImpl.loadData(isPullToRefresh);
    }

    @Override
    public void bindData(M data) {
        mvplceViewImpl.bindData(data);
    }

    public void onErrorClick() {
        loadData(false);
    }
}
