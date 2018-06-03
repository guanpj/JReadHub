package com.jeez.guanpj.jreadhub.ui.topic.detail.relate;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.ReadhubApplication;
import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.di.component.DaggerPopupWindowComponent;
import com.jeez.guanpj.jreadhub.di.module.PopupWindowModule;
import com.jeez.guanpj.jreadhub.widget.RelativePopupWindow;

import javax.inject.Inject;

public class RelevantTopicWindow extends RelativePopupWindow implements RelevantTopicContract.View {

    private String mTopicId;
    private RecyclerView mRecyclerView;
    private long mOrder;

    @Inject
    public RelevantTopicPresenter mPresenter;

    public RelevantTopicWindow(Context context, String topicId, long order) {
        this.mTopicId = topicId;
        this.mOrder = order;

        View view = LayoutInflater.from(context).inflate(R.layout.layout_topic_trace, null);
        mRecyclerView = view.findViewById(R.id.recycler_topic_trace);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Disable default animation for circular reveal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setAnimationStyle(0);
        }
        performInject();
        initData();
    }

    private void initData() {
        try {
            /*mPresenter = new RelevantTopicPresenter(new DataManager(new RetrofitHelper()))*/
            mPresenter.getRelateTopic(mTopicId, 1, mOrder, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performInject() {
        DaggerPopupWindowComponent.builder().appComponent(ReadhubApplication.getAppComponent())
                .popupWindowModule(new PopupWindowModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showOnAnchor(@NonNull View anchor, int vertPos, int horizPos, int x, int y, boolean fitInScreen) {
        super.showOnAnchor(anchor, vertPos, horizPos, x, y, fitInScreen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circularReveal(anchor);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularReveal(@NonNull final View anchor) {
        final View contentView = getContentView();
        contentView.post(new Runnable() {
            @Override
            public void run() {
                final int[] myLocation = new int[2];
                final int[] anchorLocation = new int[2];
                contentView.getLocationOnScreen(myLocation);
                anchor.getLocationOnScreen(anchorLocation);
                final int cx = anchorLocation[0] - myLocation[0] + anchor.getWidth()/2;
                final int cy = anchorLocation[1] - myLocation[1] + anchor.getHeight()/2;

                contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                final int dx = Math.max(cx, contentView.getMeasuredWidth() - cx);
                final int dy = Math.max(cy, contentView.getMeasuredHeight() - cy);
                final float finalRadius = (float) Math.hypot(dx, dy);
                Animator animator = ViewAnimationUtils.createCircularReveal(contentView, cx, cy, 0f, finalRadius);
                animator.setDuration(500);
                animator.start();
            }
        });
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestTopicEnd(RelevantTopicBean bean) {

    }

    @Override
    public void onRequestError() {

    }

    @Override
    public void onFabClick() {

    }
}
