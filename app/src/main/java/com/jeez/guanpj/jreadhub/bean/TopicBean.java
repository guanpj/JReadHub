package com.jeez.guanpj.jreadhub.bean;

import com.jeez.guanpj.jreadhub.util.FormatUtils;

import org.parceler.Parcel;
import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;

@Parcel
public class TopicBean {

    /**
     * id : 1mY1Lpcntvs
     * createdAt : 2018-04-25T02:55:26.234Z
     * nelData : {"state":true,"result":[{"weight":0.2402448058128357,"nerName":"华为","entityId":42,"entityName":"华为","entityType":"company","entityUniqueId":"baike_6455903"},{"weight":0.3688744008541107,"nerName":"EMUI","entityId":216765,"entityName":"EMUI","entityType":"product","entityUniqueId":"baike_6271355"},{"weight":0.23941493034362793,"nerName":"P20","entityId":-1,"entityName":"","entityType":"product","entityUniqueId":-1}],"nerResult":{"person":{},"company":{"华为":{"weight":0.2402448058128357}},"product":{"P20":{"weight":0.23941493034362793},"EMUI":{"weight":0.3688744008541107}},"location":{}}}
     * eventData : null
     * newsArray : [{"id":19116115,"url":"http://tech.ifeng.com/a/20180424/44967717_0.shtml","title":"华为P20获EMUI新版更新：提升吃鸡流畅性","groupId":1,"siteName":"凤凰科技","siteSlug":"site_ifeng","mobileUrl":"http://m.ifeng.com/sharenews.f?ch=qd_sdk_dl1&aid=040590044967717","authorName":"凤凰号  ","duplicateId":1,"publishDate":"2018-04-24T15:40:07.000Z"},{"id":19119899,"url":"http://app.techweb.com.cn/android/2018-04-25/2658513.shtml","title":"华为P20获EMUI新版更新：提升吃鸡流畅性","groupId":1,"siteName":"TechWeb","siteSlug":"rss_techweb","mobileUrl":"http://app.techweb.com.cn/android/2018-04-25/2658513.shtml","authorName":null,"duplicateId":1,"publishDate":"2018-04-25T00:58:12.000Z"},{"id":19120863,"url":"http://mo.techweb.com.cn/phone/2018-04-25/2658628.shtml","title":"吃鸡更流畅！华为P20获EMUI新版更新","groupId":2,"siteName":"TechWeb","siteSlug":"rss_techweb","mobileUrl":"http://mo.techweb.com.cn/phone/2018-04-25/2658628.shtml","authorName":null,"duplicateId":2,"publishDate":"2018-04-25T02:51:18.000Z"}]
     * order : 46115
     * publishDate : 2018-04-25T02:55:26.263Z
     * summary : 4月24日，华为向P20推送EMUI新版更新，下面我们来详细了解下 ... 本次更新优化了相机和音效的使用体验，并优化了部分游戏的流畅性，推荐更新。
     * title : 华为P20获EMUI新版更新：提升吃鸡流畅性
     * updatedAt : 2018-04-25T02:55:26.942Z
     * timeline : null
     * extra : {"instantView":true}
     */

    String id;
    String createdAt;
    /*Object eventData;*/
    long order;
    String publishDate;
    String summary;
    String title;
    String updatedAt;
    /*Object timeline;*/
    ArrayList<TopicNewsBean> newsArray;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /*public Object getEventData() {
        return eventData;
    }

    public void setEventData(Object eventData) {
        this.eventData = eventData;
    }*/

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    /*public String getPublishDate() {
        return publishDate;
    }*/

    public OffsetDateTime getPublishDate() {
        return FormatUtils.string2ODT(publishDate);
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /*public Object getTimeline() {
        return timeline;
    }

    public void setTimeline(Object timeline) {
        this.timeline = timeline;
    }*/

    public ArrayList<TopicNewsBean> getNewsArray() {
        return newsArray;
    }

    public void setNewsArray(ArrayList<TopicNewsBean> newsArray) {
        this.newsArray = newsArray;
    }
}
