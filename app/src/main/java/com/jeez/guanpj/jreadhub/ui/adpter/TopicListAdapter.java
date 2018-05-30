package com.jeez.guanpj.jreadhub.ui.adpter;

import android.app.Activity;
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

    private final Activity activity;
    private final LayoutInflater inflater;
    private final List<TopicBean> topicList = new ArrayList<>();
    private final SparseBooleanArray expandStateMap = new SparseBooleanArray();
    private final List<View> newsViewPool = new ArrayList<>();

    public TopicListAdapter(@NonNull Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    public List<TopicBean> getTopicList() {
        return topicList;
    }

    public void clearExpandStates() {
        expandStateMap.clear();
    }

    @NonNull
    @Override
    public BaseViewHolder<TopicBean> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicViewHolder(activity, parent);
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

        TopicViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.item_topic_trace);
        }

        @Override
        public void bindData(TopicBean topic) {
            this.topic = topic;
            this.position = getAdapterPosition();

            tvTitle.setText(topic.getTitle());
            tvSummary.setText(topic.getSummary());
            tvSummary.setVisibility(TextUtils.isEmpty(topic.getSummary()) ? View.GONE : View.VISIBLE);
            tvTime.setText(FormatUtils.getRelativeTimeSpanString(topic.getPublishDate()));
            imgInstantRead.setVisibility(topic.hasInstantView() ? View.VISIBLE : View.GONE);
            tvInfo.setText(activity.getString(R.string.source_count, topic.getNewsArray().size()));

            boolean expand = expandStateMap.get(position, false);
            imgExpandState.setImageResource(expand ? R.drawable.ic_less_info : R.drawable.ic_more_info);
            layoutExpand.setExpanded(expand, false);
            adjustLayoutSourceChildren(topic.getNewsArray().size());
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
            }
        }

        void adjustLayoutSourceChildren(int count) {
            if (layoutSource.getChildCount() < count) {
                int offset = count - layoutSource.getChildCount();
                for (int i = 0; i < offset; i++) {
                    View view;
                    if (newsViewPool.isEmpty()) {
                        view = inflater.inflate(R.layout.item_topic_news, layoutSource, false);
                    } else {
                        view = newsViewPool.remove(0);
                    }
                    layoutSource.addView(view);
                }
            } else if (layoutSource.getChildCount() > count) {
                int offset = layoutSource.getChildCount() - count;
                for (int i = 0; i < offset; i++) {
                    View view = layoutSource.getChildAt(0);
                    layoutSource.removeView(view);
                    newsViewPool.add(view);
                }
            }
        }

        @OnClick(R.id.img_instant_read)
        void onInstantReadClick() {
            /*((MainActivity) activity).findFragment(MainFragment.class)
                    .start(InstantReadFragment.newInstance(topic.getId()));*/
            InstantReadFragment.newInstance(topic.getId()).show(((MainActivity)activity).getSupportFragmentManager(),
                    InstantReadFragment.TAG);
        }

        @OnClick(R.id.ll_item_header)
        void onItemHeaderClick() {
            ((MainActivity) activity).findFragment(MainFragment.class)
                    .start(TopicDetailFragment.newInstance(topic));
        }

        @OnClick(R.id.fl_item_footer)
        void onItemFooterClick() {
            if (expandStateMap.get(position, false)) {
                expandStateMap.put(position, false);
                imgExpandState.setImageResource(R.drawable.ic_more_info);
                layoutExpand.setExpanded(false);
            } else {
                expandStateMap.put(position, true);
                imgExpandState.setImageResource(R.drawable.ic_less_info);
                layoutExpand.setExpanded(true);
            }
        }

    }

    class NewsViewHolder extends BaseViewHolder<TopicNewsBean> {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_info)
        TextView tvInfo;
        @BindView(R.id.line)
        View line;

        private TopicNewsBean news;

        NewsViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.item_topic_news);
        }

        @Override
        public void bindData(TopicNewsBean news) {
            this.news = news;
            tvTitle.setText(news.getTitle());
            tvInfo.setText(activity.getString(R.string.site_name___time, news.getSiteName(), FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            ((SupportActivity) activity).findFragment(MainFragment.class)
                    .start(CommonArticleFragment.newInstance(news.getMobileUrl()));
        }
    }

}
