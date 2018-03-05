package com.jeez.guanpj.jreadhub.base.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRefreshViewAdapter<M,L,VH extends RecyclerView.ViewHolder>
        extends BaseRecyclerAdapter<VH> {

    private Context context;
    private List<M> data;
    private LayoutInflater inflater;

    public BaseRefreshViewAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<M>();
        this.inflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public List<M> getData() {
        return data;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void refreshAdapter(boolean isPullRefresh, List<M> list, L data) {
        if (isPullRefresh){
            getData().clear();
        }
        if (list != null){
            getData().addAll(list);
        }
        //刷新
        notifyDataSetChanged();
    }

    @Override
    public int getAdapterItemCount() {
        return getData().size();
    }
}
