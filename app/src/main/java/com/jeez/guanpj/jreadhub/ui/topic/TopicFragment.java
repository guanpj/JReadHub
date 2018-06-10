package com.jeez.guanpj.jreadhub.ui.topic;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.ui.adpter.AnimTopicListAdapter;
import com.jeez.guanpj.jreadhub.ui.adpter.TopicListAdapter;
import com.jeez.guanpj.jreadhub.ui.common.article.CommonArticleFragment;
import com.jeez.guanpj.jreadhub.ui.main.MainFragment;
import com.jeez.guanpj.jreadhub.util.NavigationUtil;
import com.jeez.guanpj.jreadhub.widget.LoadMoreFooter;
import com.jeez.guanpj.jreadhub.widget.decoration.GapItemDecoration;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;

public class TopicFragment extends AbsBaseMvpFragment<TopicPresenter> implements TopicContract.View, SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener, AnimTopicListAdapter.OnNewItemClickListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView mRecyclerView;

    private LoadMoreFooter mLoadMoreFooter;
    private AnimTopicListAdapter mAdapter;

    public static TopicFragment newInstance() {
        Bundle args = new Bundle();
        TopicFragment fragment = new TopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_common;
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new GapItemDecoration(getActivity()));

        mLoadMoreFooter = new LoadMoreFooter(getContext(), mRecyclerView);
        mAdapter = new AnimTopicListAdapter();
        mAdapter.isFirstOnly(false);
        mAdapter.setNotDoAnimationCount(3);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setOnNewItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initDataAndEvent() {
        mRefreshLayout.setOnRefreshListener(this);
        mLoadMoreFooter.setOnLoadMoreListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setRefreshing(true);
        onRefresh();

    }

    @Override
    public void onRefresh() {
        mPresenter.doRefresh();
    }

    @Override
    public void onLoadMore() {
        mPresenter.doLoadMore(mAdapter.getItem(mAdapter.getItemCount() - 1).getOrder());
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd(DataListBean<TopicBean> data, boolean isPull2Refresh) {
        List<TopicBean> dataList = data.getData();
        if (null != dataList && !dataList.isEmpty()) {
            mAdapter.addData(dataList);
            mLoadMoreFooter.setState(LoadMoreFooter.STATE_ENDLESS);
        } else {
            mLoadMoreFooter.setState(LoadMoreFooter.STATE_FINISHED);
        }

        if (isPull2Refresh) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRequestError(boolean isPull2Refresh) {
        if (isPull2Refresh) {
            mRefreshLayout.setRefreshing(false);
        } else {
            mLoadMoreFooter.setState(LoadMoreFooter.STATE_FAILED);
        }
    }

    @Override
    public void onFabClick() {
        mRecyclerView.scrollToPosition(0);
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    public void onNewItemClick(String newsUrl) {
        if (mPresenter.isUseSystemBrowser()) {
            NavigationUtil.openInBrowser(getActivity(), newsUrl);
        } else {
            ((SupportActivity) getContext()).findFragment(MainFragment.class)
                    .start(CommonArticleFragment.newInstance(newsUrl));
        }
    }
}
