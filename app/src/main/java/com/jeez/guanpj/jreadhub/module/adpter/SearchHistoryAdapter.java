package com.jeez.guanpj.jreadhub.module.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.BaseAdapter;
import com.jeez.guanpj.jreadhub.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.bean.SearchHistoryBean;
import com.jeez.guanpj.jreadhub.event.DeleteSearchHistoryEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchHistoryAdapter extends BaseAdapter<SearchHistoryBean> {

    private final Context mContext;

    public SearchHistoryAdapter(@NonNull Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder<SearchHistoryBean> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mContext, parent);
    }

    class ViewHolder extends BaseViewHolder<SearchHistoryBean> {

        @BindView(R.id.tv_keyword)
        TextView mTxtKeyword;

        SearchHistoryBean mCurrentBean;

        public ViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.item_search_history);
        }

        @Override
        public void bindData(SearchHistoryBean searchHistoryBean, int position) {
            mTxtKeyword.setText(searchHistoryBean.getKeyWord());
            mCurrentBean = searchHistoryBean;
        }

        @OnClick(R.id.img_close)
        void onItemDelete() {
            RxBus.getInstance().post(new DeleteSearchHistoryEvent(mCurrentBean));
        }
    }
}
