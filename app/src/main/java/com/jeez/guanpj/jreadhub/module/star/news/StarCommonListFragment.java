package com.jeez.guanpj.jreadhub.module.star.news;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.event.OpenWebSiteEvent;
import com.jeez.guanpj.jreadhub.module.adpter.NewsListAdapterWithThirdLib;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.animator.EmptyEffect;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.fragment.AbsBaseMvpLceFragment;
import com.jeez.guanpj.jreadhub.util.ResourceUtil;
import com.jeez.guanpj.jreadhub.widget.custom.CustomLoadMoreView;

import java.util.List;

import butterknife.BindView;


public class StarCommonListFragment extends AbsBaseMvpLceFragment<List<NewsBean>, StarCommonPresenter>
        implements StarCommonContract.View<List<NewsBean>>, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private NewsListAdapterWithThirdLib mAdapter;
    private boolean isPullToRefresh;

    public static StarCommonListFragment newInstance() {
        StarCommonListFragment fragment = new StarCommonListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_star;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NewsListAdapterWithThirdLib();
        mAdapter.isFirstOnly(false);
        mAdapter.setNotDoAnimationCount(3);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(),
                ResourceUtil.getResource(getActivity(), R.attr.readhubTheme)));
    }

    @Override
    public void initDataAndEvent() {
        mAdapter.setOnLoadMoreListener(() -> doLoadMore(), mRecyclerView);
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLceSwitchEffect(EmptyEffect.getInstance());
        loadData(false);
    }

    @Override
    public void loadData(boolean isPullToRefresh) {
        mPresenter.doRefresh(isPullToRefresh);
    }

    @Override
    public void onRefresh() {
        isPullToRefresh = true;
        mRefreshLayout.setRefreshing(true);
        mAdapter.setEnableLoadMore(false);
        mPresenter.doRefresh(true);
    }

    public void doLoadMore() {
        isPullToRefresh = false;
        mPresenter.doLoadMore(mAdapter.getItem(mAdapter.getItemCount() - 2).getFormattedPublishDate().toInstant().toEpochMilli());
    }

    @Override
    public void bindData(List<NewsBean> data) {
        if (null != data && !data.isEmpty()) {
            List<NewsBean> dataList = data;
            if (isPullToRefresh) {
                mRefreshLayout.setRefreshing(false);
                mAdapter.setNewData(dataList);
                mRecyclerView.scrollToPosition(0);
                mAdapter.setEnableLoadMore(true);
            } else {
                mAdapter.addData(dataList);
                mAdapter.loadMoreComplete();
                mAdapter.setEnableLoadMore(true);
            }
        } else {
            if (isPullToRefresh) {
            } else {
                mAdapter.loadMoreEnd(false);
            }
        }
    }

    @Override
    public void onFabClick(int currentPageIndex) {
        mRecyclerView.scrollToPosition(0);
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
                RxBus.getInstance().post(new OpenWebSiteEvent(url, newsBean.getTitle()));
            }
        }
    }
}

