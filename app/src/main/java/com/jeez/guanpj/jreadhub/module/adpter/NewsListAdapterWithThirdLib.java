package com.jeez.guanpj.jreadhub.module.adpter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.util.FormatUtils;

import java.util.List;

/**
 * 使用 BRVAH 库的 Adapter
 */
public class NewsListAdapterWithThirdLib extends BaseQuickAdapter<NewsBean, BaseViewHolder> {

    public NewsListAdapterWithThirdLib() {
        super(R.layout.item_news);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            //局部刷新，这里只刷新时间
            setInfo(holder, getItem(position));
        }
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsBean newsBean) {
        holder.setText(R.id.tv_title, newsBean.getTitle().trim());
        if (TextUtils.isEmpty(newsBean.getSummaryAuto().trim())) {
            holder.setGone(R.id.tv_summary, false);
        } else {
            holder.setGone(R.id.tv_summary, true);
            holder.setText(R.id.tv_summary, newsBean.getSummaryAuto().trim());
        }

        setInfo(holder, newsBean);
    }

    private void setInfo(BaseViewHolder holder, NewsBean newsBean) {
        SpannableString spannableInfoText = null;
        if (TextUtils.isEmpty(newsBean.getAuthorName())) {
            String infoText = mContext.getString(R.string.site_name___time,
                    newsBean.getSiteName(),
                    FormatUtils.getRelativeTimeSpanString(newsBean.getFormattedPublishDate()));
            spannableInfoText = new SpannableString(infoText);
            spannableInfoText.setSpan(new StyleSpan(Typeface.BOLD),
                    0, newsBean.getSiteName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            String infoText = mContext.getString(R.string.site_name___author_name___time,
                    newsBean.getSiteName(),
                    newsBean.getAuthorName(),
                    FormatUtils.getRelativeTimeSpanString(newsBean.getFormattedPublishDate()));
            spannableInfoText = new SpannableString(infoText);
            spannableInfoText.setSpan(new StyleSpan(Typeface.BOLD),
                    0, newsBean.getSiteName().length() + newsBean.getAuthorName().length() + 3,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.setText(R.id.tv_info, spannableInfoText);
    }
}
