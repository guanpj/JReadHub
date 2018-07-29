package com.jeez.guanpj.jreadhub.module.adpter;

import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;

import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

public class TopicTimelineAdapterWithThirdLib extends BaseQuickAdapter<RelevantTopicBean, BaseViewHolder> {

    public static final int VIEW_TYPE_TOP = 1, VIEW_TYPE_BOTTOM = 2, VIEW_TYPE_ONLY_ONE = 3;

    public TopicTimelineAdapterWithThirdLib() {
        super(R.layout.item_topic_timeline);
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

    @Override
    protected void convert(BaseViewHolder holder, RelevantTopicBean relevantTopicBean) {
        LocalDate date = relevantTopicBean.getCreatedAt().toLocalDate();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        if (year == OffsetDateTime.now().getYear()) {
            holder.setText(R.id.txt_date, mContext.getString(R.string.month__day, month, day));
        } else {
            SpannableString spannableTitle = SpannableString.valueOf(mContext.getString(R.string.month__day__year, month, day, year));
            spannableTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.text_topic_detail_news_author)),
                    spannableTitle.toString().indexOf("\n") + 1,
                    spannableTitle.toString().indexOf("\n") + 5,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.setText(R.id.txt_date, spannableTitle);
        }
        holder.setText(R.id.txt_topic_trace_content, relevantTopicBean.getTitle());
        holder.setVisible(R.id.view_top_line, holder.getItemViewType() == VIEW_TYPE_TOP || holder.getItemViewType() == VIEW_TYPE_ONLY_ONE ? false : true);
        holder.setVisible(R.id.view_bottom_line, holder.getItemViewType() == VIEW_TYPE_BOTTOM || holder.getItemViewType() == VIEW_TYPE_ONLY_ONE ? false : true);
    }
}
