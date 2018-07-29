package com.jeez.guanpj.jreadhub.module.topic.detail.relate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.di.component.DaggerPopupWindowComponent;
import com.jeez.guanpj.jreadhub.event.RelevantTopicItemClickEvent;
import com.jeez.guanpj.jreadhub.module.adpter.TopicTimelineAdapterWithThirdLib;
import com.jeez.guanpj.jreadhub.module.main.MainFragment;
import com.jeez.guanpj.jreadhub.module.topic.detail.TopicDetailFragment;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.widget.RelativePopupWindow;

import java.util.List;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportActivity;

public class RelevantTopicWindow extends RelativePopupWindow implements RelevantTopicContract.View {

    private Context mContext;
    private String mTopicId;
    private RecyclerView mRecyclerView;
    private TopicTimelineAdapterWithThirdLib mAdapter;
    private View mEmptyView;
    private TextView mEmptyTipsView;
    private final View mLoadingView;
    private final View mErrorView;
    private long mOrder;

    @Inject
    public RelevantTopicPresenter mPresenter;

    public RelevantTopicWindow(Context context, String topicId, long order) {
        this.mContext = context;
        this.mTopicId = topicId;
        this.mOrder = order;

        performInject();
        if (null != mPresenter) {
            mPresenter.onAttatch(this);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.layout_topic_timeline, null);
        mRecyclerView = view.findViewById(R.id.recycler_topic_trace);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new TopicTimelineAdapterWithThirdLib();
        mRecyclerView.setAdapter(mAdapter);

        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.view_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mEmptyTipsView = mEmptyView.findViewById(R.id.txt_tips);
        mEmptyTipsView.setText("暂无数据");
        mLoadingView = LayoutInflater.from(mContext).inflate(R.layout.view_loading, (ViewGroup) mRecyclerView.getParent(), false);
        mErrorView = LayoutInflater.from(mContext).inflate(R.layout.view_error, (ViewGroup) mRecyclerView.getParent(), false);

        setContentView(view);
        setWidth(800);
        setHeight(500);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setAnimationStyle(0);
        }
        initData();
        initEvent();
    }

    private void initData() {
        mPresenter.getRelateTopic(mTopicId, 1, mOrder, System.currentTimeMillis());
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            RelevantTopicBean bean = (RelevantTopicBean) adapter.getData().get(position);
            ((SupportActivity) mContext).findFragment(MainFragment.class)
                    .start(TopicDetailFragment.newInstance(bean.getId(), bean.getTitle()));
            RxBus.getInstance().post(new RelevantTopicItemClickEvent());
            dismiss();
        });
    }

    private void performInject() {
        DaggerPopupWindowComponent.builder().appComponent(ReadhubApplicationLike.getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public void showOnAnchor(@NonNull View anchor, int vertPos, int horizPos, int x, int y, boolean fitInScreen) {
        super.showOnAnchor(anchor, vertPos, horizPos, x, y, fitInScreen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*circularReveal(anchor);*/
        }
    }

    @Override
    public void onRequestStart() {
        mAdapter.setEmptyView(mLoadingView);
    }

    @Override
    public void onRequestTopicEnd(List<RelevantTopicBean> beans) {
        if (beans != null && !beans.isEmpty()) {
            mAdapter.setNewData(beans);
        } else {
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void onRequestError() {
        mAdapter.setEmptyView(mErrorView);
    }
}
