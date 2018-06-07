package com.jeez.guanpj.jreadhub.ui.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.ui.activity.MainFragment;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.BaseAdapter;
import com.jeez.guanpj.jreadhub.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.ui.common.article.CommonArticleFragment;
import com.jeez.guanpj.jreadhub.util.FormatUtils;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

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
        public void bindData(NewsBean news, int position) {
            this.news = news;
            tvTitle.setText(news.getTitle());
            tvSummary.setText(news.getSummaryAuto());
            tvSummary.setVisibility(TextUtils.isEmpty(news.getSummaryAuto()) ? View.GONE : View.VISIBLE);
            if (TextUtils.isEmpty(news.getAuthorName())) {
                tvInfo.setText(mContext.getString(
                        R.string.site_name___time, news.getSiteName(),
                        FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
            } else {
                tvInfo.setText(mContext.getString(
                        R.string.site_name___author_name___time,
                        news.getSiteName(),
                        news.getAuthorName(),
                        FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
            }
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            ((SupportActivity) mContext).findFragment(MainFragment.class)
                    .start(CommonArticleFragment.newInstance(news.getMobileUrl()));
        }
    }
}
