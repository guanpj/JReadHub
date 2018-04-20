package com.jeez.guanpj.jreadhub.ui.test.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.support.lce.MVPLCEFragment;
import com.jeez.guanpj.mvpframework.support.lce.MVPLCEView;

public abstract class BaseMvpLceFragment<M, V extends MVPLCEView<M>, P extends MVPPresenter<V>>
        extends MVPLCEFragment<M, V, P> {

    private View viewContent;//缓存视图
    private boolean isInit;
    private boolean isPullToRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (viewContent == null) {
            viewContent = inflater.inflate(getContentView(), container, false);
            initContentView(viewContent);
        }

        ViewGroup parent = (ViewGroup) viewContent.getParent();
        if (parent != null) {
            parent.removeView(viewContent);
        }
        return viewContent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInit) {
            this.isInit = true;
            initData();
        }
    }

    public abstract int getContentView();

    public void initData() {

    }

    public boolean isPullToRefresh() {
        return isPullToRefresh;
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        super.loadData(pullToRefresh);
        this.isPullToRefresh = pullToRefresh;
    }

    public abstract void initContentView(View contentView);

    public abstract void initNavigation(View contentView);
}
