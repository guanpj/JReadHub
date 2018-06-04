package com.jeez.guanpj.jreadhub.ui.adpter;

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
import com.jeez.guanpj.jreadhub.base.BaseAdapter;
import com.jeez.guanpj.jreadhub.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.ui.topic.detail.TopicDetailFragment;

import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

import butterknife.BindView;
import butterknife.OnClick;

public class TopicTimelineAdapter extends BaseAdapter<RelevantTopicBean> {

    private final Context mContext;
    public static final int VIEW_TYPE_TOP = 1, VIEW_TYPE_BOTTOM = 2, VIEW_TYPE_ONLY_ONE = 3;

    public TopicTimelineAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public BaseViewHolder<RelevantTopicBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mContext, parent);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == 1) {
            return VIEW_TYPE_ONLY_ONE;
        }
        if (position == 0) {
            return VIEW_TYPE_TOP;
        }
        if (position == getItemCount() - 1) {
            return VIEW_TYPE_BOTTOM;
        }
        return super.getItemViewType(position);
    }

    class ViewHolder extends BaseViewHolder<RelevantTopicBean> {

        private Context mContext;
        @BindView(R.id.txt_date)
        TextView mTxtDate;
        @BindView(R.id.txt_topic_trace_content)
        TextView mTxtContent;
        @BindView(R.id.view_top_line)
        View mDividerTop;
        @BindView(R.id.view_bottom_line)
        View mDividerBottom;

        private RelevantTopicBean mTopicTrace;

        public ViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.item_topic_timeline);
            this.mContext = context;
        }

        @Override
        public void bindData(RelevantTopicBean value, int position) {
            mTopicTrace = value;
            LocalDate date = value.getCreatedAt().toLocalDate();
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();
            if (year == OffsetDateTime.now().getYear()) {
                mTxtDate.setText(mContext.getString(R.string.month__day, month, day));
            } else {
                SpannableString spannableTitle = SpannableString.valueOf(mContext.getString(R.string.month__day__year, month, day, year));
                spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#AAACB4")),
                        spannableTitle.toString().indexOf(" ") + 1,
                        spannableTitle.toString().indexOf(" ") + 5,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTxtDate.setText(spannableTitle);
            }
            mTxtContent.setText(value.getTitle());
            mDividerTop.setVisibility(getItemViewType() == VIEW_TYPE_TOP || getItemViewType() == VIEW_TYPE_ONLY_ONE ? View.INVISIBLE : View.VISIBLE);
            mDividerBottom.setVisibility(getItemViewType() == VIEW_TYPE_BOTTOM || getItemViewType() == VIEW_TYPE_ONLY_ONE ? View.INVISIBLE : View.VISIBLE);
        }

        @OnClick(R.id.txt_topic_trace_content)
        void onClickContent(View view) {
            ((MainActivity) view.getContext()).findFragment(MainFragment.class)
                    .start(TopicDetailFragment.newInstance(mTopicTrace.getId()));
        }
    }
}
