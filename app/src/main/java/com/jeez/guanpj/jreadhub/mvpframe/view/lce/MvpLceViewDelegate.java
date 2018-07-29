package com.jeez.guanpj.jreadhub.mvpframe.view.lce;

import android.view.View;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.animator.AnimatorEffect;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.animator.ILceSwitchEffect;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class MvpLceViewDelegate implements IBaseMvpLceView {

    private View loadingView;
    private View contentView;
    private View errorView;
    private View emptyView;
    private View retryView;

    private enum State {Loading, Error, Empty, Content}
    private State currentState;

    private ILceSwitchEffect lceSwitchEffect;

    public void initLceView(View view) {
        if (loadingView == null) {
            loadingView = view.findViewById(R.id.loading_view);
        }
        if (contentView == null) {
            contentView = view.findViewById(R.id.content_view);
        }
        if (errorView == null) {
            errorView = view.findViewById(R.id.error_view);
            retryView = view.findViewById(R.id.ll_wrapper);
        }
        if (emptyView == null) {
            emptyView = view.findViewById(R.id.empty_view);
            retryView = view.findViewById(R.id.ll_wrapper);
        }
        if (loadingView == null) {
            throw new NullPointerException("loadingView must be not null!");
        }
        if (contentView == null) {
            throw new NullPointerException("contentView must be not null!");
        }
        /*if (errorView == null) {
            throw new NullPointerException("errorView must be not null!");
        }
        if (emptyView == null) {
            throw new NullPointerException("emptyView must be not null!");
        }*/
    }

    public void setOnErrorViewClickListener(View.OnClickListener onClickListener) {
        if (this.retryView != null) {
            this.retryView.setOnClickListener(onClickListener);
        }
    }

    private ILceSwitchEffect getLceSwitchEffect() {
        if (lceSwitchEffect == null) {
            lceSwitchEffect = AnimatorEffect.getInstance();
        }
        return lceSwitchEffect;
    }

    public void setLceSwitchEffect(ILceSwitchEffect lceSwitchEffect) {
        this.lceSwitchEffect = lceSwitchEffect;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if (!pullToRefresh) {
            getLceSwitchEffect().showLoading(loadingView, contentView, errorView);
        }
        currentState = State.Loading;
    }

    @Override
    public void showContent() {
        if (currentState == State.Loading) {
            Observable.timer(1, TimeUnit.SECONDS).compose(RxSchedulers.observableIo2Main()).subscribe(time ->
                    getLceSwitchEffect().showContent(loadingView, contentView, errorView));
        } else {
            getLceSwitchEffect().showContent(loadingView, contentView, errorView);
        }
        currentState = State.Content;
    }

    @Override
    public void showError() {
        if (currentState == State.Loading) {
            Observable.timer(1, TimeUnit.SECONDS).compose(RxSchedulers.observableIo2Main()).subscribe(time ->
                    getLceSwitchEffect().showErrorView(loadingView, contentView, errorView));
        } else {
            getLceSwitchEffect().showErrorView(loadingView, contentView, errorView);
        }
        currentState = State.Error;
    }

    @Override
    public void showEmpty() {
        if (currentState == State.Loading) {
            Observable.timer(1, TimeUnit.SECONDS).compose(RxSchedulers.observableIo2Main()).subscribe(time ->
                    getLceSwitchEffect().showEmptyView(loadingView, contentView, emptyView));
        } else {
            getLceSwitchEffect().showEmptyView(loadingView, contentView, emptyView);
        }
        currentState = State.Empty;
    }

    @Override
    public void bindData(Object data, boolean replaceData) {

    }

    @Override
    public void loadData(boolean isPullToRefresh) {

    }
}
