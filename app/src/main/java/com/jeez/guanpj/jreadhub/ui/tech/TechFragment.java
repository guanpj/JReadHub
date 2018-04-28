package com.jeez.guanpj.jreadhub.ui.tech;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.ui.adpter.NewsListAdapter;
import com.jeez.guanpj.jreadhub.widget.LoadMoreFooter;
import com.jeez.guanpj.jreadhub.widget.decoration.GapItemDecoration;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import butterknife.BindView;

public class TechFragment extends AbsBaseMvpFragment<TechPresenter> implements TechContract.View, SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;

    private LoadMoreFooter loadMoreFooter;
    private NewsListAdapter listAdapter;

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

        loadMoreFooter = new LoadMoreFooter(getActivity(), recyclerView);
        listAdapter = new NewsListAdapter(getActivity());
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void initDataAndEvent() {
        refreshLayout.setOnRefreshListener(this);
        loadMoreFooter.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mPresenter.doRefresh();
    }

    @Override
    public void onLoadMore() {
        mPresenter.doLoadMore(listAdapter.getNewsList().get(listAdapter.getNewsList().size() - 1).getPublishDate().toInstant().toEpochMilli());
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd(DataListBean<NewsBean> data, boolean isPull2Refresh) {
        if (isPull2Refresh) {
            listAdapter.getNewsList().clear();
            listAdapter.getNewsList().addAll(data.getData());
            listAdapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
            loadMoreFooter.setState(data.getData().isEmpty() ? LoadMoreFooter.STATE_DISABLED : LoadMoreFooter.STATE_ENDLESS);
        } else {
            int startPosition = listAdapter.getItemCount();
            listAdapter.getNewsList().addAll(data.getData());
            listAdapter.notifyItemRangeInserted(startPosition, data.getData().size());
            loadMoreFooter.setState(data.getData().isEmpty() ? LoadMoreFooter.STATE_FINISHED : LoadMoreFooter.STATE_ENDLESS);
        }
    }

    @Override
    public void onRequestError(boolean isPull2Refresh) {
        if (isPull2Refresh) {
            refreshLayout.setRefreshing(false);
        } else {
            loadMoreFooter.setState(LoadMoreFooter.STATE_FAILED);
        }
    }

    @Override
    public void onFabClick() {
        recyclerView.scrollToPosition(0);
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
            onRefresh();
        }
    }
}
