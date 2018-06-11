package com.jeez.guanpj.jreadhub.ui.adpter;

import android.text.TextUtils;

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
            holder.setText(R.id.tv_info, mContext.getString(
                    R.string.site_name___time, newsBean.getSiteName(),
                    FormatUtils.getRelativeTimeSpanString(newsBean.getPublishDate())));
        } else {
            holder.setText(R.id.tv_info, mContext.getString(
                    R.string.site_name___author_name___time,
                    newsBean.getSiteName(),
                    newsBean.getAuthorName(),
                    FormatUtils.getRelativeTimeSpanString(newsBean.getPublishDate())));
        }
    }
}
