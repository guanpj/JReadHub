package com.jeez.guanpj.jreadhub.ui.topic.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.BaseAdapter;
import com.jeez.guanpj.jreadhub.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicNewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicTraceBean;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.util.Constants;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;

public class TopicDetailFragment extends AbsBaseMvpFragment<TopicDetailPresenter> implements TopicDetailContract.View {

    public static final int VIEW_TYPE_TOP = 99, VIEW_TYPE_BOTTOM = 98;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_topic_detail_title)
    TextView mTxtTopicTitle;
    @BindView(R.id.txt_topic_time)
    TextView mTxtTopicTime;
    @BindView(R.id.txt_topic_description)
    TextView mTxtTopicDescription;
    @BindView(R.id.linear_web_title_container)
    LinearLayout mLinearTitleContainer;
    @BindView(R.id.linear_time_line_container)
    LinearLayout mLinearTimelineContainer;
    @BindView(R.id.recycler_time_line)
    RecyclerView mRecyclerTimeline;
    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;

    private TopicBean mTopic;
    private BaseAdapter<TopicTraceBean> mTimelineAdapter = new BaseAdapter<TopicTraceBean>() {
        @Override
        public BaseViewHolder<TopicTraceBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TopicTraceViewHolder(getContext(), parent);
        }

        @Override public int getItemViewType(int position) {
            if (position == 0) return VIEW_TYPE_TOP;
            if (position == getItemCount() - 1) return VIEW_TYPE_BOTTOM;
            return super.getItemViewType(position);
        }
    };

    public static TopicDetailFragment newInstance(TopicBean topic) {
        TopicDetailFragment fragment = new TopicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_TOPIC, Parcels.wrap(topic));
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TopicDetailFragment newInstance(String topicId) {
        TopicDetailFragment fragment = new TopicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_TOPIC_ID, topicId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTopic = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_TOPIC));
        if (mTopic != null) {
            onRequestTopicEnd(mTopic);
            return;
        }
        String topicId = getArguments().getString(Constants.BUNDLE_TOPIC_ID);
        mPresenter.getTopicDetail(topicId);
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_topic_detail;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initDataAndEvent() {
        mTxtTopicTitle.setText(mTopic.getTitle());
        mTxtTopicTime.setText(mTopic.getPublishDate().toString());
        mTxtTopicDescription.setText(mTopic.getSummary());
        mTxtTopicDescription.setVisibility(
                TextUtils.isEmpty(mTopic.getSummary()) ? View.GONE : View.VISIBLE);
        mLinearTitleContainer.removeAllViews();
        for (final TopicNewsBean topic : mTopic.getNewsArray()) {
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setPadding(10, 16, 10, 16);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_data, 0, 0, 0);
            textView.setCompoundDrawablePadding(15);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(Color.parseColor("#607D8B"));
            textView.setBackgroundResource(android.R.drawable.list_selector_background);
            if (TextUtils.isEmpty(topic.getSiteName())) {
                textView.setText(topic.getTitle());
            } else {
                SpannableString spannableTitle =
                        SpannableString.valueOf(topic.getTitle() + " " + topic.getSiteName());
                spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#AAACB4")),
                        topic.getTitle().length() + 1,
                        topic.getTitle().length() + topic.getSiteName().length() + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableTitle);
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //start(WebViewFragment.newInstance(topic));
                }
            });
            mLinearTitleContainer.addView(textView);
        }
        mRecyclerTimeline.setAdapter(mTimelineAdapter);
        mRecyclerTimeline.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerTimeline.setNestedScrollingEnabled(false);
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                /*mTxtToolbarTitle.setVisibility(
                        scrollY > mTxtTopicTime.getBottom() ? View.VISIBLE : View.GONE);
                mImgToolbar.setVisibility(scrollY > mTxtTopicTime.getBottom() ? View.GONE : View.VISIBLE);*/
            }
        });
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestTopicEnd(TopicBean bean) {
        mTopic = bean;
        initDataAndEvent();
        mPresenter.getTopicTrace(bean.getId());
    }

    @Override
    public void onRequestTopicTraceEnd(List<TopicTraceBean> beans) {
        mTimelineAdapter.addItems(beans);
        mLinearTimelineContainer.setVisibility(
                mTimelineAdapter.getItemCount() != 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRequestError() {
        showShortToast("请求错误");
    }

    @Override
    public void onFabClick() {

    }
}
