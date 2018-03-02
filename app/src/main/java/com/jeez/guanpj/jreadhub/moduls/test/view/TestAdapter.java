package com.jeez.guanpj.jreadhub.moduls.test.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.moduls.base.view.BaseRefreshViewAdapter;
import com.jeez.guanpj.jreadhub.moduls.test.bean.PostModel;
import com.jeez.guanpj.jreadhub.moduls.test.util.DateUtils;
import com.jeez.guanpj.jreadhub.moduls.test.util.ImageLoaderUtils;

public class TestAdapter extends BaseRefreshViewAdapter<PostModel.ListBean, PostModel,TestAdapter.VideoAdapterViewHolder> {

    public enum Type {
        Text("段子", 29);
        public String title;
        public int type;

        Type(String title, int type) {
            this.title = title;
            this.type = type;
        }
    }

    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public VideoAdapterViewHolder getViewHolder(View view) {
        return new VideoAdapterViewHolder(view, false);
    }

    @Override
    public VideoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = getInflater().inflate(
                R.layout.item_essence_layout, parent, false);
        VideoAdapterViewHolder holder = new VideoAdapterViewHolder(v, true);
        return holder;
    }

    @Override
    public void onBindViewHolder(final VideoAdapterViewHolder holder, int position, boolean isItem) {
        final PostModel.ListBean postList = getData().get(position);
        ImageLoaderUtils.getInstance().loadHeaderImage(getContext(), postList.getProfile_image(), holder.iv_header);
        holder.tv_name.setText(postList.getName());
        holder.tv_time.setText(DateUtils.parseDate(postList.getCreate_time()));
        holder.tv_content.setText(postList.getText());
        holder.tv_like.setText(postList.getDing());
        holder.tv_dislike.setText(postList.getCai());
        holder.tv_forword.setText(postList.getRepost());
        holder.tv_comment.setText(postList.getComment());
    }

    public class VideoAdapterViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_header;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_content;

        public LinearLayout ll_like;
        public TextView tv_like;

        public LinearLayout ll_dislike;
        public TextView tv_dislike;

        public LinearLayout ll_forword;
        public TextView tv_forword;

        public LinearLayout ll_comment;
        public TextView tv_comment;

        public VideoAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                iv_header = (ImageView) itemView.findViewById(R.id.iv_header);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);
                tv_content = (TextView) itemView.findViewById(R.id.tv_content);
                ll_like = (LinearLayout) itemView.findViewById(R.id.ll_like);
                tv_like = (TextView) itemView.findViewById(R.id.tv_like);
                ll_dislike = (LinearLayout) itemView.findViewById(R.id.ll_dislike);
                tv_dislike = (TextView) itemView.findViewById(R.id.tv_dislike);
                ll_forword = (LinearLayout) itemView.findViewById(R.id.ll_forword);
                tv_forword = (TextView) itemView.findViewById(R.id.tv_forword);
                ll_comment = (LinearLayout) itemView.findViewById(R.id.ll_comment);
                tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            }
        }
    }
}
