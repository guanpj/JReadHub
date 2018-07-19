package com.jeez.guanpj.jreadhub.module.topic;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.module.adpter.TopicListAdapterWithThirdLib;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.animator.EmptyEffect;
import com.jeez.guanpj.jreadhub.mvpframe.view.lce.fragment.AbsBaseMvpLceFragment;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.ResourceUtil;
import com.jeez.guanpj.jreadhub.widget.custom.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.reactivex.Observable;

public class TopicFragment extends AbsBaseMvpLceFragment<DataListBean<TopicBean>, TopicPresenter> implements TopicContract.View<DataListBean<TopicBean>>, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_new)
    TextView mTxtNew;

    private TopicListAdapterWithThirdLib mAdapter;
    private boolean isPullToRefresh;

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
        mAdapter = new TopicListAdapterWithThirdLib();
        mAdapter.isFirstOnly(false);
        mAdapter.setNotDoAnimationCount(3);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(),
                ResourceUtil.getResource(getActivity(), R.attr.readhubTheme)));
    }

    @Override
    public void initDataAndEvent() {
        mAdapter.setOnLoadMoreListener(() -> doLoadMore(), mRecyclerView);
        mRefreshLayout.setOnRefreshListener(this);

        // 轮询获取新话题提示
        mPresenter.addSubscribe(Observable.interval(30, TimeUnit.SECONDS)
                .filter(time -> Constants.TOPIC_TOP_COUNT >= 0 && null != mAdapter.getItem(Constants.TOPIC_TOP_COUNT))
                .subscribe(time -> mPresenter.getNewTopicCount(mAdapter.getItem(Constants.TOPIC_TOP_COUNT).getOrder())));

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
        mPresenter.doLoadMore(mAdapter.getItem(mAdapter.getItemCount() - 2).getOrder());
    }

    @Override
    public void bindData(DataListBean<TopicBean> data) {
        if (null != data) {
            if (null != data.getData() && !data.getData().isEmpty()) {
                List<TopicBean> dataList = data.getData();
                if (isPullToRefresh) {
                    mTxtNew.setVisibility(View.GONE);
                    mRefreshLayout.setRefreshing(false);
                    mAdapter.setNewData(dataList);
                    mRecyclerView.scrollToPosition(0);
                    //mPresenter.getDiffResult(mAdapter.getData(), dataList);
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
        } else {
            if (isPullToRefresh) {
            } else {
                mAdapter.loadMoreFail();
            }
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
                mRecyclerView.scrollToPosition(0);
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
                mAdapter.notifyItemMoved(fromPosition, toPosition);
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
    }

    @Override
    public void showNewTopicCount(int newTopicCount) {
        mTxtNew.setVisibility(View.VISIBLE);
        mTxtNew.setText(getContext().getString(R.string.new_topic_tips, newTopicCount));
    }

    @OnClick(R.id.txt_new)
    void onClick() {
        mTxtNew.setVisibility(View.GONE);
        mRecyclerView.scrollToPosition(0);
        mRefreshLayout.setRefreshing(true);
        onRefresh();
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
