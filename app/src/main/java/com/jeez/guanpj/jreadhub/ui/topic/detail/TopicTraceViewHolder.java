package com.jeez.guanpj.jreadhub.ui.topic.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.bean.TopicTraceBean;

import butterknife.BindView;
import butterknife.OnClick;

public class TopicTraceViewHolder extends BaseViewHolder<TopicTraceBean> {
    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.txt_time_line_content)
    TextView mTxtContent;
    @BindView(R.id.view_top_line)
    View mDividerTop;
    @BindView(R.id.view_bottom_line)
    View mDividerBottom;

    private TopicTraceBean mTopicTrace;

    public TopicTraceViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_topic_trace);
    }

    @Override
    public void bindData(TopicTraceBean value) {
        mTopicTrace = value;
        mTxtDate.setText(value.date);
        mTxtContent.setText(value.content);
        mDividerTop.setVisibility(
                getItemViewType() == TopicDetailFragment.VIEW_TYPE_TOP ? View.INVISIBLE : View.VISIBLE);
        mDividerBottom.setVisibility(
                getItemViewType() == TopicDetailFragment.VIEW_TYPE_BOTTOM ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick(R.id.txt_time_line_content)
    void onClickContent(View view) {
    /*((MainActivity) view.getContext()).findFragment(MainFragment.class)
        .start(TopicDetailFragment.newInstance(mTopicTrace.url));*/
    }
}
