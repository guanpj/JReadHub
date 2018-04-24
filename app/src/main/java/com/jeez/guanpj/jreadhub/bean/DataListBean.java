package com.jeez.guanpj.jreadhub.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataListBean<T> {
    @SerializedName("data")
    private List<T> dataList;

    private int pageSize;

    private int totalItems;

    private int totalPages;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
