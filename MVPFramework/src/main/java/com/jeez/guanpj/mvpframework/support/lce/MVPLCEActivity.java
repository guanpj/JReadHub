package com.jeez.guanpj.mvpframework.support.lce;

import android.view.View;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;
import com.jeez.guanpj.mvpframework.support.MVPActivity;

public abstract class MVPLCEActivity<M, V extends MVPView, P extends MVPPresenter<V>>
        extends MVPActivity<V, P> implements MVPLCEView<M> {

    private MVPLCEViewImpl<M> mvplceViewImpl;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        if (mvplceViewImpl == null) {
            mvplceViewImpl = new MVPLCEViewImpl<>();
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
