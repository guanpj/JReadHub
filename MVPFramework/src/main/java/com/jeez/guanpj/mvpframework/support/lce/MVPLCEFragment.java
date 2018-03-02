package com.jeez.guanpj.mvpframework.support.lce;

import android.os.Bundle;
import android.view.View;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;
import com.jeez.guanpj.mvpframework.support.MVPFragment;
import com.jeez.guanpj.mvpframework.support.lce.animator.ILCEAnimator;

public abstract class MVPLCEFragment<M, V extends MVPView, P extends MVPPresenter<V>>
        extends MVPFragment<V, P> implements MVPLCEView<M> {
    private MVPLCEViewImpl<M> mvplceViewImpl;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mvplceViewImpl == null) {
            mvplceViewImpl = new MVPLCEViewImpl<>();
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
