package com.jeez.guanpj.jreadhub.ui.adpter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.MainFragment;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.ui.common.article.CommonArticleFragment;
import com.jeez.guanpj.jreadhub.util.FormatUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private final Activity activity;
    private final LayoutInflater inflater;
    private final List<NewsBean> newsList = new ArrayList<>();

    public NewsListAdapter(@NonNull Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    public List<NewsBean> getNewsList() {
        return newsList;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newsList.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_summary)
        TextView tvSummary;
        @BindView(R.id.tv_info)
        TextView tvInfo;

        private NewsBean news;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull NewsBean news) {
            this.news = news;
            tvTitle.setText(news.getTitle());
            tvSummary.setText(news.getSummaryAuto());
            tvSummary.setVisibility(TextUtils.isEmpty(news.getSummaryAuto()) ? View.GONE : View.VISIBLE);
            if (TextUtils.isEmpty(news.getAuthorName())) {
                tvInfo.setText(activity.getString(R.string.site_name___time, news.getSiteName(), FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
            } else {
                tvInfo.setText(activity.getString(R.string.site_name___author_name___time, news.getSiteName(), news.getAuthorName(), FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
            }
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            ((SupportActivity) activity).findFragment(MainFragment.class)
                    .start(CommonArticleFragment.newInstance(news.getMobileUrl()));
        }
    }
}
