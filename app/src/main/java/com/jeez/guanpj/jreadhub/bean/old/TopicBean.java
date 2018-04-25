package com.jeez.guanpj.jreadhub.bean.old;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.OffsetDateTime;

import java.util.List;

public class TopicBean {

    private String id;

    private OffsetDateTime createdAt;

    private TopicNelData nelData;

    @SerializedName("newsArray")
    private List<TopicNews> newsList;

    private long order;

    private OffsetDateTime publishDate;

    @SerializedName("relatedTopicArray")
    private List<Object> relatedTopicList;

    private String summary;

    private String title;

    private OffsetDateTime updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TopicNelData getNelData() {
        return nelData;
    }

    public void setNelData(TopicNelData nelData) {
        this.nelData = nelData;
    }

    public List<TopicNews> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<TopicNews> newsList) {
        this.newsList = newsList;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public OffsetDateTime getPublishDate() {
        return publishDate == null ? getUpdatedAt() : publishDate; // TODO 发布时间有可能为空？
    }

    public void setPublishDate(OffsetDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public List<Object> getRelatedTopicList() {
        return relatedTopicList;
    }

    public void setRelatedTopicList(List<Object> relatedTopicList) {
        this.relatedTopicList = relatedTopicList;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
