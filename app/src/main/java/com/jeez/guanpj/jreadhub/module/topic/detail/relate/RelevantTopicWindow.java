package com.jeez.guanpj.jreadhub.module.topic.detail.relate;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.di.component.DaggerPopupWindowComponent;
import com.jeez.guanpj.jreadhub.event.RelevantTopicItemClickEvent;
import com.jeez.guanpj.jreadhub.module.adpter.TopicTimelineAdapter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.widget.RelativePopupWindow;

import java.util.List;

import javax.inject.Inject;

public class RelevantTopicWindow extends RelativePopupWindow implements RelevantTopicContract.View {

    private Context mContext;
    private String mTopicId;
    private RecyclerView mRecyclerView;
    private TopicTimelineAdapter mAdapter;
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
        mAdapter = new TopicTimelineAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

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
        RxBus.getInstance().toFlowable(RelevantTopicItemClickEvent.class).subscribe(relevantTopicItemClickEvent -> {
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

    }

    @Override
    public void onRequestTopicEnd(List<RelevantTopicBean> bean) {
        mAdapter.addItems(bean);
    }

    @Override
    public void onRequestError() {

    }
}
