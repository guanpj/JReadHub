package com.jeez.guanpj.jreadhub.module.adpter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.BaseAdapter;
import com.jeez.guanpj.jreadhub.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.event.OpenWebSiteEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.util.FormatUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsListAdapter extends BaseAdapter<NewsBean> {

    private final Context mContext;

    public NewsListAdapter(@NonNull Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder<NewsBean> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mContext, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<NewsBean> holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    class ViewHolder extends BaseViewHolder<NewsBean> {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_summary)
        TextView tvSummary;
        @BindView(R.id.tv_info)
        TextView tvInfo;

        private NewsBean news;

        ViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.item_news);
        }

        @Override
        public void bindData(NewsBean newsBean, int position) {
            this.news = newsBean;
            tvTitle.setText(newsBean.getTitle());

            if (!TextUtils.isEmpty(newsBean.getSummaryAuto())) {
                tvSummary.setVisibility(View.GONE);
                tvSummary.setText(newsBean.getSummaryAuto());
            } else {
                tvSummary.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(newsBean.getAuthorName())) {
                String infoText = mContext.getString(R.string.site_name___time,
                        newsBean.getSiteName(),
                        FormatUtils.getRelativeTimeSpanString(newsBean.getPublishDate()));
                SpannableString spannableInfoText = new SpannableString(infoText);
                spannableInfoText.setSpan(new StyleSpan(Typeface.BOLD),
                        0, newsBean.getSiteName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvInfo.setText(spannableInfoText);
            } else {
                String infoText = mContext.getString(R.string.site_name___author_name___time,
                        newsBean.getSiteName(),
                        newsBean.getAuthorName(),
                        FormatUtils.getRelativeTimeSpanString(newsBean.getPublishDate()));
                SpannableString spannableInfoText = new SpannableString(infoText);
                spannableInfoText.setSpan(new StyleSpan(Typeface.BOLD),
                        0, newsBean.getSiteName().length() + newsBean.getAuthorName().length() + 3,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvInfo.setText(spannableInfoText);
            }
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            String url = null;
            if (!TextUtils.isEmpty(news.getMobileUrl())) {
                url = news.getMobileUrl();
            } else {
                url = news.getUrl();
            }
            if (!TextUtils.isEmpty(url)) {
                RxBus.getInstance().post(new OpenWebSiteEvent(url, news.getTitle()));
            }
            /*((SupportActivity) mContext).findFragment(MainFragment.class)
                    .start(CommonWebViewFragment.newInstance(news.getMobileUrl()));*/
        }
    }
}
