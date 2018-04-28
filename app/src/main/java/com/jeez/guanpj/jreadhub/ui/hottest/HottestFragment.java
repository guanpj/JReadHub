package com.jeez.guanpj.jreadhub.ui.hottest;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.ui.adpter.TopicListAdapter;
import com.jeez.guanpj.jreadhub.widget.LoadMoreFooter;
import com.jeez.guanpj.jreadhub.widget.decoration.GapItemDecoration;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import butterknife.BindView;

public class HottestFragment extends AbsBaseMvpFragment<HottestPresenter> implements HottestContract.View, SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;

    private LoadMoreFooter loadMoreFooter;
    private TopicListAdapter listAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hottest;
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new GapItemDecoration(getActivity()));
        //recyclerView.addOnScrollListener(new FloatingTipButtonBehaviorListener.ForRecyclerView(btnBackToTopAndRefresh));

        loadMoreFooter = new LoadMoreFooter(getActivity(), recyclerView);
        listAdapter = new TopicListAdapter(getActivity());
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
        mPresenter.doLoadMore(listAdapter.getTopicList().get(listAdapter.getTopicList().size() - 1).getOrder());
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd(DataListBean<TopicBean> data, boolean isPull2Refresh) {
        if (isPull2Refresh) {
            listAdapter.getTopicList().clear();
            listAdapter.getTopicList().addAll(data.getData());
            listAdapter.clearExpandStates();
            listAdapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
            loadMoreFooter.setState(data.getData().isEmpty() ? LoadMoreFooter.STATE_DISABLED : LoadMoreFooter.STATE_ENDLESS);
        } else {
            int startPosition = listAdapter.getItemCount();
            listAdapter.getTopicList().addAll(data.getData());
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
