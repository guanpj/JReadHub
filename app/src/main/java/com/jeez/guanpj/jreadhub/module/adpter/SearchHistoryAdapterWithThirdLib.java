package com.jeez.guanpj.jreadhub.module.adpter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.SearchHistoryBean;

public class SearchHistoryAdapterWithThirdLib extends BaseQuickAdapter<SearchHistoryBean, BaseViewHolder> {

    public SearchHistoryAdapterWithThirdLib() {
        super(R.layout.item_search_history);
    }

    @Override
    protected void convert(BaseViewHolder holder, SearchHistoryBean item) {
        holder.setText(R.id.tv_keyword, item.getKeyWord());
        holder.addOnClickListener(R.id.img_close);
    }
}
