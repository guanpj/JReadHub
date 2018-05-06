package com.jeez.guanpj.jreadhub.ui.adpter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicNewsBean;
import com.jeez.guanpj.jreadhub.ui.common.article.CommonArticleFragment;
import com.jeez.guanpj.jreadhub.ui.topic.detail.TopicDetailFragment;
import com.jeez.guanpj.jreadhub.util.FormatUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.TopicViewHolder> {

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

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicViewHolder(inflater.inflate(R.layout.item_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        holder.bind(topicList.get(position), position);
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_summary)
        TextView tvSummary;
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

        TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull TopicBean topic, int position) {
            this.topic = topic;
            this.position = position;
            tvTitle.setText(topic.getTitle());
            tvSummary.setText(topic.getSummary());
            tvSummary.setVisibility(TextUtils.isEmpty(topic.getSummary()) ? View.GONE : View.VISIBLE);
            tvInfo.setText(activity.getString(R.string.time___source_count, FormatUtils.getRelativeTimeSpanString(topic.getPublishDate()), topic.getNewsArray().size()));
            //tvInfo.setText(topic.getPublishDate());
            boolean expand = expandStateMap.get(position, false);
            imgExpandState.setImageResource(expand ? R.mipmap.ic_expand_less_grey600_18dp : R.mipmap.ic_expand_more_grey600_18dp);
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
                holder.bind(news);
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

        @OnClick(R.id.ll_item_header)
        void onItemHeaderClick() {
            ((MainActivity) activity).findFragment(MainFragment.class)
                    .start(TopicDetailFragment.newInstance(topic));
        }

        @OnClick(R.id.fl_item_footer)
        void onItemFooterClick() {
            if (expandStateMap.get(position, false)) {
                expandStateMap.put(position, false);
                imgExpandState.setImageResource(R.mipmap.ic_expand_more_grey600_18dp);
                layoutExpand.setExpanded(false);
            } else {
                expandStateMap.put(position, true);
                imgExpandState.setImageResource(R.mipmap.ic_expand_less_grey600_18dp);
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

        void bind(@NonNull TopicNewsBean news) {
            this.news = news;
            tvTitle.setText(news.getTitle());
            tvInfo.setText(activity.getString(R.string.site_name___time, news.getSiteName(), FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
            //tvInfo.setText(news.getPublishDate());
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            ((SupportActivity) activity).findFragment(MainFragment.class)
                    .start(CommonArticleFragment.newInstance(news.getMobileUrl()));
        }
    }

}
