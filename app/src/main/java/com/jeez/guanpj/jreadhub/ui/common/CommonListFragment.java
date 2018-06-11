package com.jeez.guanpj.jreadhub.ui.common;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.event.OpenWebSiteEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.ui.adpter.AnimNewsListAdapter;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.widget.LoadMoreFooter;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.util.List;

import butterknife.BindView;


public class CommonListFragment extends AbsBaseMvpFragment<CommonPresenter> implements CommonContract.View, SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView mRecyclerView;

    private LoadMoreFooter mLoadMoreFooter;
    private AnimNewsListAdapter mAdapter;
    private @NewsBean.Type String mNewsType;

    public static CommonListFragment newInstance(@NewsBean.Type String type) {
        CommonListFragment fragment = new CommonListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_NEWS_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle && !TextUtils.isEmpty(bundle.getString(Constants.BUNDLE_NEWS_TYPE))) {
            mNewsType = bundle.getString(Constants.BUNDLE_NEWS_TYPE);
        }
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_common;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.addItemDecoration(new GapItemDecoration(getActivity()));
        //mRecyclerView.addOnScrollListener(new FloatingTipButtonBehaviorListener.ForRecyclerView(btnBackToTopAndRefresh));

        mLoadMoreFooter = new LoadMoreFooter(getActivity(), mRecyclerView);
        mAdapter = new AnimNewsListAdapter();
        mAdapter.isFirstOnly(false);
        mAdapter.setNotDoAnimationCount(3);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setOnItemClickListener(this);
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
        mPresenter.doRefresh(mNewsType);
    }

    @Override
    public void onLoadMore() {
        mPresenter.doLoadMore(mNewsType, mAdapter.getItem(mAdapter.getItemCount() - 1).getPublishDate().toInstant().toEpochMilli());
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd(DataListBean<NewsBean> data, boolean isPull2Refresh) {
        List<NewsBean> dataList = data.getData();
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (null != adapter.getData() && null != adapter.getData().get(position)) {
            NewsBean newsBean = (NewsBean) adapter.getData().get(position);
            String url = null;
            if (!TextUtils.isEmpty(newsBean.getMobileUrl())) {
                url = newsBean.getMobileUrl();
            } else {
                url = newsBean.getUrl();
            }
            if (!TextUtils.isEmpty(url)) {
                RxBus.getInstance().post(new OpenWebSiteEvent(url));
            }
        }
    }
}

