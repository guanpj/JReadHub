package com.jeez.guanpj.jreadhub.base.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.support.lce.MVPLCEView;

public abstract class BaseRefreshLCEFragment<M, V extends MVPLCEView<M>, P extends MVPPresenter<V>>
        extends BaseMPVLCEFragment<M,V,P> {

    private XRefreshView refreshView;
    private RecyclerView recyclerView;
    private BaseRefreshViewAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    public XRefreshView getRefreshView() {
        return refreshView;
    }

    public BaseRefreshViewAdapter getAdapter() {
        return recyclerViewAdapter;
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return linearLayoutManager;
    }

    private boolean isDownRefresh = true;

    public boolean isDownRefresh() {
        return isDownRefresh;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取数据
    }

    @Override
    public void initContentView(View contentView) {
        initNavigation(contentView);
        initRefreshView(contentView);
    }

    @Override
    public void initNavigation(View contentView) {

    }

    public void initRefreshView(View contentView) {
        refreshView = (XRefreshView) contentView.findViewById(R.id.xrefreshview);
        //是否可以下拉刷新,true代表可以,false代表不支持
        refreshView.setPullRefreshEnable(true);
        //是否允许加载更多
        refreshView.setPullLoadEnable(true);
        //设置下拉刷新完成之后,刷新头部停留的时间
        refreshView.setPinnedTime(1000);
        //设置是否自动刷新(进入页面自动刷新)
        refreshView.setAutoRefresh(false);

        recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        //设置列表为垂直方向显示
        recyclerView.setLayoutManager(linearLayoutManager);


        //绑定Adapter
        recyclerViewAdapter = bindAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        //给我们的Adapter添加加载更多的布局
        recyclerViewAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));

        //添加下拉刷新监听
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            /**
             * 下拉刷新
             */
            @Override
            public void onRefresh() {
                super.onRefresh();
                refreshData(true);
            }

            /**
             * 上拉加载更多
             * @param isSlience
             */
            @Override
            public void onLoadMore(boolean isSlience) {
                super.onLoadMore(isSlience);
                refreshData(false);
            }
        });
    }

    //提供给子类决定是否需要下拉刷新功能
    public void setRefresh(boolean isRefresh) {
        //是否可以下拉刷新,true代表可以,false代表不支持
        refreshView.setPullRefreshEnable(isRefresh);
        //是否允许加载更多
        refreshView.setPullLoadEnable(isRefresh);
    }

    //提供给子类设置条目分割线的高度
    public void setDidiver(int height) {
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(height));
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        super.loadData(pullToRefresh);
    }

    public abstract BaseRefreshViewAdapter bindAdapter();

    @Override
    public void bindData(M data) {
        super.bindData(data);
        //如果你是下拉刷新组件,那么我就处理
        //刷新UI界面
        if (isDownRefresh()) {
            //网络请求完成,关闭下拉刷新组件加载视图
            getRefreshView().stopRefresh();
        } else {
            getRefreshView().stopLoadMore();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存数据
    }

    public void refreshData(boolean isDownRefresh) {
        this.isDownRefresh = isDownRefresh;
    }
}
