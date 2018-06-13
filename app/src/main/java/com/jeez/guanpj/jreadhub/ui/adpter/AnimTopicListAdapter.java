package com.jeez.guanpj.jreadhub.ui.adpter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicNewsBean;
import com.jeez.guanpj.jreadhub.event.OpenWebSiteEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.ui.main.MainActivity;
import com.jeez.guanpj.jreadhub.ui.main.MainFragment;
import com.jeez.guanpj.jreadhub.ui.topic.detail.TopicDetailFragment;
import com.jeez.guanpj.jreadhub.ui.topic.instant.InstantReadFragment;
import com.jeez.guanpj.jreadhub.util.FormatUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class AnimTopicListAdapter extends BaseQuickAdapter<TopicBean, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    private final SparseBooleanArray mExtendStateMap = new SparseBooleanArray();

    public AnimTopicListAdapter() {
        super(R.layout.item_topic);
    }

    @Override
    protected void convert(BaseViewHolder holder, TopicBean topicBean) {
        int position = holder.getAdapterPosition();
        int newsCount = 0;
        String mediaName = "";
        if (null != topicBean.getNewsArray() && !topicBean.getNewsArray().isEmpty()) {
            newsCount = topicBean.getNewsArray().size();
            mediaName = topicBean.getNewsArray().get(0).getSiteName();
        }

        holder.setText(R.id.tv_title, topicBean.getTitle());
        holder.setText(R.id.tv_summary, topicBean.getSummary());
        holder.setVisible(R.id.tv_summary, TextUtils.isEmpty(topicBean.getSummary()) ? false : true);
        holder.setText(R.id.tv_time, FormatUtils.getRelativeTimeSpanString(topicBean.getPublishDate()));

        if (newsCount == 0) {
            holder.setGone(R.id.line, true);
            holder.setGone(R.id.fl_item_footer, true);
        } else if (newsCount == 1){
            holder.setText(R.id.tv_info, mContext.getString(R.string.single__media___report, mediaName));
        } else {
            holder.setText(R.id.tv_info, mContext.getString(R.string.multi__media___report, mediaName, newsCount));
        }

        holder.setVisible(R.id.img_instant_read, topicBean.hasInstantView() ? true : false);
        holder.setOnClickListener(R.id.img_instant_read, v ->
                InstantReadFragment.newInstance(topicBean.getId()).show(((MainActivity) mContext).getSupportFragmentManager(),
                InstantReadFragment.TAG));
        holder.setOnClickListener(R.id.ll_item_header, v -> ((SupportActivity) mContext).findFragment(MainFragment.class)
                .start(TopicDetailFragment.newInstance(topicBean.getId())));

        boolean expand = mExtendStateMap.get(position, false);

        ImageView imgExpandState = holder.getView(R.id.img_expand_state);
        holder.setImageResource(R.id.img_expand_state, expand ? R.drawable.ic_less_info  : R.drawable.ic_more_info);

        ExpandableLayout layoutExpand = holder.getView(R.id.layout_expand);
        layoutExpand.setExpanded(expand, false);

        holder.setOnClickListener(R.id.fl_item_footer, v -> {
            if (mExtendStateMap.get(position, false)) {
                mExtendStateMap.put(position, false);
                imgExpandState.setImageResource(R.drawable.ic_more_info);
                layoutExpand.setExpanded(false);
            } else {
                mExtendStateMap.put(position, true);
                imgExpandState.setImageResource(R.drawable.ic_less_info);
                layoutExpand.setExpanded(true);
            }
        });

        ArrayList<TopicNewsBean> newsList = topicBean.getNewsArray();
        LinearLayout layoutSource = holder.getView(R.id.layout_source);
        layoutSource.removeAllViews();
        for (int i = 0; i < newsList.size(); i++) {
            View newsItemView = mLayoutInflater.inflate(R.layout.item_topic_news, null, false);
            layoutSource.addView(newsItemView);

            TopicNewsBean news = newsList.get(i);
            NewsViewHolder newsViewHolder = (NewsViewHolder) newsItemView.getTag();
            if (newsViewHolder == null) {
                newsViewHolder = new NewsViewHolder(newsItemView);
                newsItemView.setTag(newsViewHolder);
            }
            newsViewHolder.bindData(news);
            if (newsList.size() > 3 && i == 2) {
                View newsMoreView = mLayoutInflater.inflate(R.layout.item_topic_news_more, null, false);
                newsMoreView.setOnClickListener(v ->
                        ((SupportActivity) mContext).findFragment(MainFragment.class)
                                .start(TopicDetailFragment.newInstance(topicBean.getId()))
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
            String infoText = mContext.getString(R.string.site_name___time, news.getSiteName(),
                    FormatUtils.getRelativeTimeSpanString(news.getPublishDate()));
            tvTitle.setText(news.getTitle());

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
            RxBus.getInstance().post(new OpenWebSiteEvent(news.getMobileUrl()));
        }
    }
}
