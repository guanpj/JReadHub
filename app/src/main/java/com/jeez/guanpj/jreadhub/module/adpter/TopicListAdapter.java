package com.jeez.guanpj.jreadhub.module.adpter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.BaseAdapter;
import com.jeez.guanpj.jreadhub.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicNewsBean;
import com.jeez.guanpj.jreadhub.event.OpenWebSiteEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.module.main.MainActivity;
import com.jeez.guanpj.jreadhub.module.main.MainFragment;
import com.jeez.guanpj.jreadhub.module.topic.detail.TopicDetailFragment;
import com.jeez.guanpj.jreadhub.module.topic.instant.InstantReadFragment;
import com.jeez.guanpj.jreadhub.util.FormatUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class TopicListAdapter extends BaseAdapter<TopicBean> {

    //每个 Topic 下显示报道的最大数量
    private static final int MOST_NEWS_COUNT_PER_ITEM = 3;

    private final Context mContext;
    private final LayoutInflater mInflater;

    public TopicListAdapter(@NonNull Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public BaseViewHolder<TopicBean> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicViewHolder(mContext, parent);
    }

    class TopicViewHolder extends BaseViewHolder<TopicBean> {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_summary)
        TextView tvSummary;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.img_instant_read)
        ImageView imgInstantRead;
        @BindView(R.id.line)
        View mLineView;
        @BindView(R.id.fl_item_footer)
        FrameLayout mItemFooterLayout;
        @BindView(R.id.tv_info)
        TextView tvInfo;
        @BindView(R.id.img_expand_state)
        ImageView imgExpandState;
        @BindView(R.id.layout_expand)
        ExpandableLayout layoutExpand;
        @BindView(R.id.layout_source)
        ViewGroup layoutSource;

        private TopicBean topic;
        private View newsMoreView;

        TopicViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.item_topic);
            newsMoreView = mInflater.inflate(R.layout.item_topic_news_more, null, false);
        }

        @Override
        public void bindData(TopicBean topic, int position) {
            this.topic = topic;
            int newsCount = 0;
            String mediaName = "";
            if (null != topic.getNewsArray() && !topic.getNewsArray().isEmpty()) {
                newsCount = topic.getNewsArray().size();
                mediaName = topic.getNewsArray().get(0).getSiteName();
            }

            tvTitle.setText(topic.getTitle());
            tvSummary.setText(topic.getSummary());
            tvSummary.setVisibility(TextUtils.isEmpty(topic.getSummary()) ? View.GONE : View.VISIBLE);
            tvTime.setText(FormatUtils.getRelativeTimeSpanString(topic.getFormattedPublishDate()));
            imgInstantRead.setVisibility(topic.hasInstantView() ? View.VISIBLE : View.GONE);

            if (newsCount == 0) {
                mLineView.setVisibility(View.GONE);
                mItemFooterLayout.setVisibility(View.GONE);
            } else if (newsCount == 1){
                tvInfo.setText(mContext.getString(R.string.single__media___report, mediaName));
            } else {
                tvInfo.setText(mContext.getString(R.string.multi__media___report, mediaName, newsCount));
            }

            imgExpandState.setImageResource( R.drawable.ic_topic_list_more_info);
            layoutExpand.setExpanded(false);

            layoutSource.removeAllViews();
            ArrayList<TopicNewsBean> newsList = topic.getNewsArray();
            for (int i = 0; i < newsList.size(); i++) {
                View newsItemView = mInflater.inflate(R.layout.item_topic_news, null, false);
                layoutSource.addView(newsItemView);

                TopicNewsBean news = newsList.get(i);
                NewsViewHolder newsViewHolder = (NewsViewHolder) newsItemView.getTag();
                if (newsViewHolder == null) {
                    newsViewHolder = new NewsViewHolder(newsItemView);
                    newsItemView.setTag(newsViewHolder);
                }
                newsViewHolder.bindData(news);
                if (newsList.size() > MOST_NEWS_COUNT_PER_ITEM && i == MOST_NEWS_COUNT_PER_ITEM - 1) {
                    newsMoreView.setOnClickListener(v ->
                            ((SupportActivity) mContext).findFragment(MainFragment.class)
                                    .start(TopicDetailFragment.newInstance(topic.getId(), topic.getTitle()))
                    );
                    layoutSource.addView(newsMoreView);
                    break;
                } else {
                    if (i == newsList.size() - 1) {
                        newsViewHolder.setLineVisibility(View.INVISIBLE);
                    }
                }
            }
        }

        @OnClick(R.id.img_instant_read)
        void onInstantReadClick() {
            InstantReadFragment.newInstance(topic.getId()).show(((MainActivity) mContext).getSupportFragmentManager(),
                    InstantReadFragment.TAG);
        }

        @OnClick(R.id.ll_item_header)
        void onItemHeaderClick() {
            ((SupportActivity) mContext).findFragment(MainFragment.class)
                    .start(TopicDetailFragment.newInstance(topic.getId(), topic.getTitle()));
        }

        @OnClick(R.id.fl_item_footer)
        void onItemFooterClick() {
            if (layoutExpand.getState() == ExpandableLayout.State.COLLAPSED) {
                imgExpandState.setImageResource(R.drawable.ic_topic_list_less_info);
                layoutExpand.setExpanded(true);
            } else if (layoutExpand.getState() == ExpandableLayout.State.EXPANDED) {
                imgExpandState.setImageResource(R.drawable.ic_topic_list_more_info);
                layoutExpand.setExpanded(false);
            }
        }
    }

    class NewsViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_info)
        TextView tvInfo;
        @BindView(R.id.line)
        View line;

        private TopicNewsBean news;

        NewsViewHolder(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
        }

        void bindData(@NonNull TopicNewsBean news) {
            this.news = news;
            tvTitle.setText(news.getTitle());
            String infoText = mContext.getString(R.string.site_name___time, news.getSiteName(),
                    FormatUtils.getRelativeTimeSpanString(news.getPublishDate()));

            SpannableString spannableInfoText = new SpannableString(infoText);
            spannableInfoText.setSpan(new StyleSpan(Typeface.BOLD),
                    0, news.getSiteName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvInfo.setText(spannableInfoText);
        }

        public void setLineVisibility(int visibility) {
            line.setVisibility(visibility);
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            RxBus.getInstance().post(new OpenWebSiteEvent(news.getMobileUrl(), news.getTitle()));
            /*((SupportActivity) mContext).findFragment(MainFragment.class)
                    .start(CommonWebViewFragment.newInstance(news.getMobileUrl()));*/
        }
    }

}
