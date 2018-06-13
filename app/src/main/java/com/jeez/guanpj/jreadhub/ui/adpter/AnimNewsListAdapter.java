package com.jeez.guanpj.jreadhub.ui.adpter;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.util.FormatUtils;

public class AnimNewsListAdapter extends BaseQuickAdapter<NewsBean, BaseViewHolder> {

    public AnimNewsListAdapter() {
        super(R.layout.item_news);
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsBean newsBean) {
        holder.setText(R.id.tv_title, newsBean.getTitle());
        if (!TextUtils.isEmpty(newsBean.getSummaryAuto())) {
            holder.setGone(R.id.tv_summary, true);
            holder.setText(R.id.tv_summary, newsBean.getSummaryAuto());
        } else {
            holder.setGone(R.id.tv_summary, false);
        }

        if (TextUtils.isEmpty(newsBean.getAuthorName())) {
            String infoText = mContext.getString(R.string.site_name___time,
                    newsBean.getSiteName(),
                    FormatUtils.getRelativeTimeSpanString(newsBean.getPublishDate()));
            SpannableString spannableInfoText = new SpannableString(infoText);
            spannableInfoText.setSpan(new StyleSpan(Typeface.BOLD),
                    0, newsBean.getSiteName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.setText(R.id.tv_info, spannableInfoText);
        } else {
            String infoText = mContext.getString(R.string.site_name___author_name___time,
                    newsBean.getSiteName(),
                    newsBean.getAuthorName(),
                    FormatUtils.getRelativeTimeSpanString(newsBean.getPublishDate()));
            SpannableString spannableInfoText = new SpannableString(infoText);
            spannableInfoText.setSpan(new StyleSpan(Typeface.BOLD),
                    0, newsBean.getSiteName().length() + newsBean.getAuthorName().length() + 3,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.setText(R.id.tv_info, spannableInfoText);
        }
    }
}
