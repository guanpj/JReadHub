package com.jeez.guanpj.jreadhub.ui.hottest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.old.DataListBean;
import com.jeez.guanpj.jreadhub.bean.old.TopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.ui.adpter.TopicListAdapter;
import com.jeez.guanpj.jreadhub.ui.test.widget.LoadMoreFooter;
import com.jeez.guanpj.jreadhub.widget.decoration.GapItemDecoration;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import butterknife.BindView;

public class HottestFragment extends AbsBaseMvpFragment<HottestPresenter> implements HottestContract.View, SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;
    @BindView(R.id.floating_action_btn)
    FloatingActionButton floatingActionButton;

    private LoadMoreFooter loadMoreFooter;
    private TopicListAdapter listAdapter;

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
    public void showLoading(boolean isPullToRefresh) {
    }

    @Override
    public void showContent() {
    }

    @Override
    public void showError() {

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
    public void onRequestEnd(DataListBean<TopicBean> data, boolean isPull2Refresh) {
        if (isPull2Refresh) {
            listAdapter.getTopicList().clear();
            listAdapter.getTopicList().addAll(data.getDataList());
            listAdapter.clearExpandStates();
            listAdapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
            loadMoreFooter.setState(data.getDataList().isEmpty() ? LoadMoreFooter.STATE_DISABLED : LoadMoreFooter.STATE_ENDLESS);
        } else {
            int startPosition = listAdapter.getItemCount();
            listAdapter.getTopicList().addAll(data.getDataList());
            listAdapter.notifyItemRangeInserted(startPosition, data.getDataList().size());
            loadMoreFooter.setState(data.getDataList().isEmpty() ? LoadMoreFooter.STATE_FINISHED : LoadMoreFooter.STATE_ENDLESS);
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
}
