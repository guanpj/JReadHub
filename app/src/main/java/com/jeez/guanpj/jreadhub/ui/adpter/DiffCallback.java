package com.jeez.guanpj.jreadhub.ui.adpter;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.TopicBean;

import java.util.List;

public class DiffCallback extends DiffUtil.Callback {
    private List<TopicBean> mOldData, mNewData;

    public DiffCallback(List<TopicBean> oldData, List<TopicBean> newData) {
        this.mOldData = oldData;
        this.mNewData = newData;
    }

    @Override
    public int getOldListSize() {
        return mOldData != null ? mOldData.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewData != null ? mNewData.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldData.get(oldItemPosition).getId().equals(mNewData.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (!mOldData.get(oldItemPosition).getTitle().equals(mNewData.get(newItemPosition).getTitle())) {
            return false;
        }
        return true;
    }
}
