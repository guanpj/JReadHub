package com.jeez.guanpj.jreadhub.ui.topic.detail;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.MainActivity;
import com.jeez.guanpj.jreadhub.MainFragment;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.bean.RelateTopicBean;

import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

import butterknife.BindView;
import butterknife.OnClick;

public class TopicTraceViewHolder extends BaseViewHolder<RelateTopicBean> {

    private Context mContext;
    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.txt_topic_trace_content)
    TextView mTxtContent;
    @BindView(R.id.view_top_line)
    View mDividerTop;
    @BindView(R.id.view_bottom_line)
    View mDividerBottom;

    private RelateTopicBean mTopicTrace;

    public TopicTraceViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_topic_trace);
        this.mContext = context;
    }

    @Override
    public void bindData(RelateTopicBean value, int position) {
        mTopicTrace = value;
        LocalDate date = value.getCreatedAt().toLocalDate();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        if (year == OffsetDateTime.now().getYear()) {
            mTxtDate.setText(mContext.getString(R.string.month__day, month, day));
        } else {
            SpannableString spannableTitle = SpannableString.valueOf(mContext.getString(R.string.month__day__year, month, day, year));
            spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#AAACB4")),5,9,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTxtDate.setText(spannableTitle);
        }
        mTxtContent.setText(value.getTitle());
        mDividerTop.setVisibility(getItemViewType() == TopicDetailFragment.VIEW_TYPE_TOP ? View.INVISIBLE : View.VISIBLE);
        mDividerBottom.setVisibility(getItemViewType() == TopicDetailFragment.VIEW_TYPE_BOTTOM ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick(R.id.txt_topic_trace_content)
    void onClickContent(View view) {
        ((MainActivity) view.getContext()).findFragment(MainFragment.class)
                .start(TopicDetailFragment.newInstance(mTopicTrace.getId()));
    }
}
