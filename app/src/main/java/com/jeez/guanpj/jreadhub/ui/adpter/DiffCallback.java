package com.jeez.guanpj.jreadhub.ui.adpter;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.BaseItemBean;

import java.util.ArrayList;
import java.util.List;

public class DiffCallback<T extends BaseItemBean> extends DiffUtil.Callback {
    private List<BaseItemBean> mOldData, mNewData;

    public DiffCallback(List<T> oldData, List<T> newData) {
        List<BaseItemBean> temp = new ArrayList<>();
        temp.addAll(oldData);
        this.mOldData = temp;
        temp = new ArrayList<>();
        temp.addAll(newData);
        this.mNewData = temp;
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
