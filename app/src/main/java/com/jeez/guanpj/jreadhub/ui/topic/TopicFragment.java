package com.jeez.guanpj.jreadhub.ui.topic;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.ui.adpter.TopicListAdapterWithThirdLib;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.ResourceUtil;
import com.jeez.guanpj.jreadhub.widget.custom.CustomLoadMoreView;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.reactivex.Observable;

public class TopicFragment extends AbsBaseMvpFragment<TopicPresenter> implements TopicContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView mRecyclerView;
    @BindView(R.id.txt_new)
    TextView mTxtNew;

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    private TopicListAdapterWithThirdLib mAdapter;

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
        mAdapter = new TopicListAdapterWithThirdLib();
        mAdapter.isFirstOnly(false);
        mAdapter.setNotDoAnimationCount(3);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(),
                ResourceUtil.getResource(getActivity(), R.attr.readhubTheme)));

        mLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.view_loading, (ViewGroup) mRecyclerView.getParent(), false);
        mEmptyView = LayoutInflater.from(getContext()).inflate(R.layout.view_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mErrorView = LayoutInflater.from(getContext()).inflate(R.layout.view_error, (ViewGroup) mRecyclerView.getParent(), false);
    }

    @Override
    public void initDataAndEvent() {
        mAdapter.setOnLoadMoreListener(() -> doMore(), mRecyclerView);
        mRefreshLayout.setOnRefreshListener(this);

        mEmptyView.setOnClickListener(v -> onRefresh());
        mErrorView.setOnClickListener(v -> onRefresh());

        onRefresh();
        // 30 秒轮询获取新话题数量
        mPresenter.addSubscribe(Observable.interval(15, TimeUnit.SECONDS)
                .subscribe(aLong -> mPresenter.getNewTopicCount(mAdapter.getItem(Constants.TOPIC_TOP_COUNT).getOrder())));
    }


    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        mAdapter.setEnableLoadMore(false);
        mPresenter.doRefresh();
    }

    public void doMore() {
        mPresenter.doLoadMore(mAdapter.getItem(mAdapter.getItemCount() - 2).getOrder());
    }

    @Override
    public void onRequestStart() {
        mAdapter.setEmptyView(mLoadingView);
    }

    @Override
    public void onRequestEnd(DataListBean<TopicBean> data, boolean isPull2Refresh) {
        List<TopicBean> dataList = data.getData();
        if (null != dataList && !dataList.isEmpty()) {
            if (isPull2Refresh) {
                mRefreshLayout.setRefreshing(false);
                //mAdapter.setNewData(dataList);
                mPresenter.getDiffResult(mAdapter.getData(), dataList);
            } else {
                mAdapter.addData(dataList);
                mAdapter.loadMoreComplete();
            }
        } else {
            if (isPull2Refresh) {
                mAdapter.setEmptyView(mEmptyView);
            } else {
                mAdapter.loadMoreEnd(true);
            }
        }
        mAdapter.setEnableLoadMore(true);
    }

    @Override
    public void onRequestError(boolean isPull2Refresh) {
        if (isPull2Refresh) {
            mRefreshLayout.setRefreshing(false);
            mAdapter.setEmptyView(mErrorView);
        } else {
            mAdapter.loadMoreFail();
        }
    }

    @Override
    public void onDiffResult(DiffUtil.DiffResult diffResult, List<TopicBean> newData) {
        diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
            /**
             * 只处理插入的数据
             */
            @Override
            public void onInserted(int position, int count) {
                List<TopicBean> changeList = new ArrayList<>();
                for (int i = position; i < position + count; i++) {
                    TopicBean bean = newData.get(i);
                    //置顶的话题
                    if (bean.getOrder() > 1000000) {
                        Constants.TOPIC_TOP_COUNT++;
                    }
                    changeList.add(bean);
                }
                mAdapter.addData(position, changeList);
            }

            @Override
            public void onRemoved(int position, int count) {
                if (position < Constants.TOPIC_PAGE_SIZE) {
                    Constants.TOPIC_TOP_COUNT -= count;
                    mAdapter.notifyItemRangeRemoved(position, count);
                }
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
                mAdapter.notifyItemRangeChanged(position, count, payload);
            }
        });
    }

    @Override
    public void onFabClick() {
        mRecyclerView.scrollToPosition(0);
        mTxtNew.setVisibility(View.GONE);
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

    @OnTouch(R.id.txt_new)
    boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Drawable drawableRight = mTxtNew.getCompoundDrawables()[2];
            if (drawableRight != null && event.getRawX() >= (mTxtNew.getRight() - drawableRight.getBounds().width())) {
                mTxtNew.setVisibility(View.GONE);
                return true;
            }
        }
        return false;
    }
}
