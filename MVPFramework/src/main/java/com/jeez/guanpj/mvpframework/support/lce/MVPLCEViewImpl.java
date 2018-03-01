package com.jeez.guanpj.mvpframework.support.lce;

import android.view.View;

import com.jeez.guanpj.mvpframework.R;
import com.jeez.guanpj.mvpframework.support.lce.animator.DefaultLceAnimator;
import com.jeez.guanpj.mvpframework.support.lce.animator.ILCEAnimator;

public class MVPLCEViewImpl<M> implements MVPLCEView<M> {

    private View loadingView;
    private View contentView;
    private View errorView;

    private ILCEAnimator lceAnimator;

    public void initLceView(View v) {
        if (loadingView == null) {
            loadingView = v.findViewById(R.id.loadingView);
        }
        if (contentView == null) {
            contentView = v.findViewById(R.id.contentView);
        }
        if (errorView == null) {
            errorView = v.findViewById(R.id.errorView);
        }
        if (loadingView == null) {
            throw new NullPointerException("loadingView not found!");
        }
        if (contentView == null) {
            throw new NullPointerException("contentView not found!");
        }
        if (errorView == null) {
            throw new NullPointerException("errorView not found!");
        }
    }

    public void setOnErrorViewClickListener(View.OnClickListener onClickListener) {
        if (this.errorView != null) {
            this.errorView.setOnClickListener(onClickListener);
        }
    }

    private ILCEAnimator getLceAnimator() {
        if (lceAnimator == null) {
            lceAnimator = DefaultLceAnimator.getInstance();
        }
        return lceAnimator;
    }

    /**
     * 绑定动画执行策略
     *
     * @param lceAnimator
     */
    public void setLceAnimator(ILCEAnimator lceAnimator) {
        this.lceAnimator = lceAnimator;
    }

    @Override
    public void showLoading(boolean isPullToRefresh) {
        if(!isPullToRefresh){
            getLceAnimator().showLoading(loadingView, contentView, errorView);
        }
    }

    @Override
    public void showContent() {
        getLceAnimator().showContent(loadingView, contentView, errorView);
    }

    @Override
    public void showError() {
        getLceAnimator().showErrorView(loadingView, contentView, errorView);
    }

    @Override
    public void bindData(M data) {

    }

    @Override
    public void loadData(boolean isPullToRefresh) {

    }
}
