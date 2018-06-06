package com.jeez.guanpj.jreadhub.ui.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.MainActivity;
import com.jeez.guanpj.jreadhub.MainFragment;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.BaseAdapter;
import com.jeez.guanpj.jreadhub.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicNewsBean;
import com.jeez.guanpj.jreadhub.ui.common.article.CommonArticleFragment;
import com.jeez.guanpj.jreadhub.ui.topic.detail.TopicDetailFragment;
import com.jeez.guanpj.jreadhub.ui.topic.instant.InstantReadFragment;
import com.jeez.guanpj.jreadhub.util.FormatUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class TopicListAdapter extends BaseAdapter<TopicBean> {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final SparseBooleanArray mExtendStateMap = new SparseBooleanArray();
    private final List<View> mViewCache = new ArrayList<>();

    public TopicListAdapter(@NonNull Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void clearExpandStates() {
        mExtendStateMap.clear();
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
        @BindView(R.id.tv_info)
        TextView tvInfo;
        @BindView(R.id.img_expand_state)
        ImageView imgExpandState;
        @BindView(R.id.layout_expand)
        ExpandableLayout layoutExpand;
        @BindView(R.id.layout_source)
        ViewGroup layoutSource;

        private TopicBean topic;
        private int position;

        private View newsMoreView;

        TopicViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.item_topic_expand);
            newsMoreView = mInflater.inflate(R.layout.item_topic_news_more, null, false);
        }

        @Override
        public void bindData(TopicBean topic, int position) {
            this.topic = topic;
            this.position = position;

            tvTitle.setText(topic.getTitle());
            tvSummary.setText(topic.getSummary());
            tvSummary.setVisibility(TextUtils.isEmpty(topic.getSummary()) ? View.GONE : View.VISIBLE);
            tvTime.setText(FormatUtils.getRelativeTimeSpanString(topic.getPublishDate()));
            imgInstantRead.setVisibility(topic.hasInstantView() ? View.VISIBLE : View.GONE);
            tvInfo.setText(mContext.getString(R.string.source_count, topic.getNewsArray().size()));

            boolean expand = mExtendStateMap.get(this.position, false);
            imgExpandState.setImageResource(expand ? R.drawable.ic_less_info : R.drawable.ic_more_info);
            layoutExpand.setExpanded(expand, false);

            layoutSource.removeAllViews();
            if (topic.getNewsArray().size() > 3) {
                for (int i = 0; i < 3; i++) {
                    View newsItemView = mInflater.inflate(R.layout.item_topic_news, layoutSource, false);
                    layoutSource.addView(newsItemView);

                    TopicNewsBean news = topic.getNewsArray().get(i);
                    NewsViewHolder holder = (NewsViewHolder) newsItemView.getTag();
                    if (holder == null) {
                        holder = new NewsViewHolder(newsItemView);
                        newsItemView.setTag(holder);
                    }
                    holder.bindData(news);
                }
                newsMoreView.setOnClickListener(v ->
                    ((SupportActivity) mContext).findFragment(MainFragment.class)
                            .start(TopicDetailFragment.newInstance(topic.getId()))
                );
                layoutSource.addView(newsMoreView);
            } else {
                for (int i = 0; i < topic.getNewsArray().size(); i++) {
                    View newsItemView = mInflater.inflate(R.layout.item_topic_news, layoutSource, false);
                    layoutSource.addView(newsItemView);

                    TopicNewsBean news = topic.getNewsArray().get(i);
                    NewsViewHolder holder = (NewsViewHolder) newsItemView.getTag();
                    if (holder == null) {
                        holder = new NewsViewHolder(newsItemView);
                        newsItemView.setTag(holder);
                    }
                    holder.bindData(news);
                    if (i == topic.getNewsArray().size() - 1) {
                        holder.setLineVisibility(View.INVISIBLE);
                    }
                }
            }

            /*adjustLayoutSourceChildren(topic.getNewsArray().size());
            for (int i = 0; i < layoutSource.getChildCount(); i++) {
                TopicNewsBean news = topic.getNewsArray().get(i);
                View  view = layoutSource.getChildAt(i);
                NewsViewHolder holder = (NewsViewHolder) view.getTag();
                if (holder == null) {
                    holder = new NewsViewHolder(view);
                    view.setTag(holder);
                }
                holder.bindData(news);
                if (i == layoutSource.getChildCount() - 1) {
                    holder.line.setBackground(null);
                }
            }*/
        }

        void adjustLayoutSourceChildren(int count) {
            if (layoutSource.getChildCount() < count) {
                int offset = count - layoutSource.getChildCount();
                for (int i = 0; i < offset; i++) {
                    View view;
                    if (mViewCache.isEmpty()) {
                        view = mInflater.inflate(R.layout.item_topic_news, layoutSource, false);
                    } else {
                        view = mViewCache.remove(0);
                    }
                    layoutSource.addView(view);
                }
            } else if (layoutSource.getChildCount() > count) {
                int offset = layoutSource.getChildCount() - count;
                for (int i = 0; i < offset; i++) {
                    View view = layoutSource.getChildAt(0);
                    layoutSource.removeView(view);
                    mViewCache.add(view);
                }
            }
        }

        @OnClick(R.id.img_instant_read)
        void onInstantReadClick() {
            /*((MainActivity) mContext).findFragment(MainFragment.class)
                    .start(InstantReadFragment.newInstance(topic.getId()));*/
            InstantReadFragment.newInstance(topic.getId()).show(((MainActivity) mContext).getSupportFragmentManager(),
                    InstantReadFragment.TAG);
        }

        @OnClick(R.id.ll_item_header)
        void onItemHeaderClick() {
            ((SupportActivity) mContext).findFragment(MainFragment.class)
                    .start(TopicDetailFragment.newInstance(topic.getId()));
            /*((MainFragment) mParentFragment).startBrotherFragment(TopicDetailFragment.newInstance(topic.getId()));*/
        }

        @OnClick(R.id.fl_item_footer)
        void onItemFooterClick() {
            if (mExtendStateMap.get(position, false)) {
                mExtendStateMap.put(position, false);
                imgExpandState.setImageResource(R.drawable.ic_more_info);
                layoutExpand.setExpanded(false);
            } else {
                mExtendStateMap.put(position, true);
                imgExpandState.setImageResource(R.drawable.ic_less_info);
                layoutExpand.setExpanded(true);
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
            tvInfo.setText(mContext.getString(R.string.site_name___time, news.getSiteName(), FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
        }

        public void setLineVisibility(int visibility) {
            line.setVisibility(visibility);
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            ((SupportActivity) mContext).findFragment(MainFragment.class)
                    .start(CommonArticleFragment.newInstance(news.getMobileUrl()));
        }
    }

}
