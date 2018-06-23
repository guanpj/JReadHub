package com.jeez.guanpj.jreadhub.ui.topic;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.ui.adpter.AnimTopicListAdapter;
import com.jeez.guanpj.jreadhub.ui.adpter.DiffCallback;
import com.jeez.guanpj.jreadhub.widget.custom.CustomLoadMoreView;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class TopicFragment extends AbsBaseMvpFragment<TopicPresenter> implements TopicContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView mRecyclerView;
    @BindView(R.id.txt_new)
    TextView mTxtNew;

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
        //mRecyclerView.addItemDecoration(new GapItemDecoration(getActivity()));

        mAdapter = new AnimTopicListAdapter();
        mAdapter.isFirstOnly(false);
        mAdapter.setNotDoAnimationCount(3);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initDataAndEvent() {
        mAdapter.setOnLoadMoreListener(() -> doMore(), mRecyclerView);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setRefreshing(true);
        onRefresh();
        // 30 秒轮询获取新话题数量
        mPresenter.addSubscribe(Observable.interval(30, TimeUnit.SECONDS)
                .subscribe(aLong -> mPresenter.getNewTopicCount(mAdapter.getItem(0).getOrder())));
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mPresenter.doRefresh();
    }

    public void doMore() {
        mPresenter.doLoadMore(mAdapter.getItem(mAdapter.getItemCount() - 2).getOrder());
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd(DataListBean<TopicBean> data, boolean isPull2Refresh) {
        List<TopicBean> dataList = data.getData();
        if (null != dataList && !dataList.isEmpty()) {
            if (isPull2Refresh) {
                mRefreshLayout.setRefreshing(false);
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(mAdapter.getData(), dataList), false);
                diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
                    @Override
                    public void onInserted(int position, int count) {
                        List<TopicBean> changeList = new ArrayList<>();
                        for (int i = position; i < position + count; i++) {
                            changeList.add(dataList.get(i));
                        }
                        mAdapter.addData(position, changeList);
                    }

                    @Override
                    public void onRemoved(int position, int count) {
                    }

                    @Override
                    public void onMoved(int fromPosition, int toPosition) {
                    }

                    @Override
                    public void onChanged(int position, int count, Object payload) {
                    }
                });
            } else {
                mAdapter.addData(dataList);
                mAdapter.loadMoreComplete();
            }
        } else {
            if (!isPull2Refresh) {
                mAdapter.loadMoreEnd(true);
            }
        }
        mAdapter.setEnableLoadMore(true);
    }

    @Override
    public void onRequestError(boolean isPull2Refresh) {
        if (isPull2Refresh) {
            mRefreshLayout.setRefreshing(false);
        } else {
            mAdapter.loadMoreFail();
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
    public void showNewTopicCount(int newTopicCount) {
        mTxtNew.setVisibility(View.VISIBLE);
        mTxtNew.setText(getContext().getString(R.string.new_topic_tips, newTopicCount));
    }

    @OnClick(R.id.txt_new)
    void onClick() {
        mRecyclerView.scrollToPosition(0);
        mTxtNew.setVisibility(View.GONE);
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }
}
