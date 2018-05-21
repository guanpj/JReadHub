package com.jeez.guanpj.mvpframework.support.lce;

import android.view.View;

import com.jeez.guanpj.mvpframework.base.presenter.MvpPresenter;
import com.jeez.guanpj.mvpframework.base.view.MvpView;
import com.jeez.guanpj.mvpframework.support.MvpActivity;

public abstract class MvpLCEActivity<M, V extends MvpView, P extends MvpPresenter<V>>
        extends MvpActivity<V, P> implements MvpLCEView<M> {

    private MvpLCEViewImpl<M> mvplceViewImpl;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        if (mvplceViewImpl == null) {
            mvplceViewImpl = new MvpLCEViewImpl<>();
        }
        initLCEView(getWindow().getDecorView());
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

    public void onErrorClick() {
        loadData(false);
    }
}
