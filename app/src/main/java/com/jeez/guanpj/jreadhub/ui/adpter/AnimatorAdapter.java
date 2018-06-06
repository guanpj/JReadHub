package com.jeez.guanpj.jreadhub.ui.adpter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.ExpandTopicBean;
import com.jeez.guanpj.jreadhub.bean.ExpandTopicNewsBean;
import com.jeez.guanpj.jreadhub.util.FormatUtils;

import java.util.List;

public class AnimatorAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    private Context mContext;
    private LayoutInflater mInflater;

    private ExpandTopicBean topic;
    private int position;

    private View newsMoreView;

    public AnimatorAdapter(Context context, @Nullable List<MultiItemEntity> data) {
        super(data);
        this.mContext = context;
        addItemType(TYPE_LEVEL_0, R.layout.item_topic);
        addItemType(TYPE_LEVEL_1, R.layout.item_topic_news);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                ExpandTopicBean topic = (ExpandTopicBean) item;
                holder.setText(R.id.tv_title, topic.getTitle());
                holder.setText(R.id.tv_summary, topic.getSummary());
                holder.setVisible(R.id.tv_summary, TextUtils.isEmpty(topic.getSummary()) ? false : true);
                holder.setText(R.id.tv_time, FormatUtils.getRelativeTimeSpanString(topic.getPublishDate()));
                holder.setText(R.id.tv_info, mContext.getString(R.string.source_count, topic.getNewsArray().size()));
                holder.setVisible(R.id.img_instant_read, topic.hasInstantView() ? true : false);
                holder.addOnClickListener(R.id.img_instant_read);
                holder.setImageResource(R.id.img_expand_state, topic.isExpanded() ? R.drawable.ic_less_info  : R.drawable.ic_more_info);
                holder.setOnClickListener(R.id.ll_item_header, v -> {
                    int pos = holder.getAdapterPosition();
                    if (topic.isExpanded()) {
                        collapse(pos);
                    } else {
                        expand(pos);
                    }
                });
                break;
            case TYPE_LEVEL_1:
                ExpandTopicNewsBean news = (ExpandTopicNewsBean) item;
                holder.setText(R.id.tv_title, news.getTitle());
                holder.setText(R.id.tv_info, mContext.getString(R.string.site_name___time, news.getSiteName(),
                        FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
                /*holder.setLineVisibility(View.INVISIBLE);*/
                break;
            default:
                break;
        }
    }
}
