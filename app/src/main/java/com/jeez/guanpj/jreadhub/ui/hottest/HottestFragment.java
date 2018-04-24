package com.jeez.guanpj.jreadhub.ui.hottest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.widget.decoration.GapItemDecoration;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import butterknife.BindView;

public class HottestFragment extends AbsBaseMvpFragment<HottestPresenter> {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;
    @BindView(R.id.floating_action_btn)
    FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hottest;
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new GapItemDecoration(getActivity()));
        //recyclerView.addOnScrollListener(new FloatingTipButtonBehaviorListener.ForRecyclerView(btnBackToTopAndRefresh));
    }

    @Override
    public void initDataAndEvent() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError() {

    }
}
