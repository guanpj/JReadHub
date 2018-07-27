package com.jeez.guanpj.jreadhub.bean;

import android.arch.persistence.room.Entity;

import com.jeez.guanpj.jreadhub.util.FormatUtils;

import org.threeten.bp.OffsetDateTime;

@Entity(tableName = "topic_news")
public class TopicNewsBean implements Cloneable {

    /**
     * id : 19116115
     * url : http://tech.ifeng.com/a/20180424/44967717_0.shtml
     * title : 华为P20获EMUI新版更新：提升吃鸡流畅性
     * groupId : 1
     * siteName : 凤凰科技
     * siteSlug : site_ifeng
     * mobileUrl : http://m.ifeng.com/sharenews.f?ch=qd_sdk_dl1&aid=040590044967717
     * authorName : 凤凰号
     * duplicateId : 1
     * publishDate : 2018-04-24T15:40:07.000Z
     */

    private int id;
    private String url;
    private String title;
    private int groupId;
    private String siteName;
    private String siteSlug;
    private String mobileUrl;
    private String authorName;
    private int duplicateId;
    private String publishDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteSlug() {
        return siteSlug;
    }

    public void setSiteSlug(String siteSlug) {
        this.siteSlug = siteSlug;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getDuplicateId() {
        return duplicateId;
    }

    public void setDuplicateId(int duplicateId) {
        this.duplicateId = duplicateId;
    }

    /*public String getFormattedPublishDate() {
        return publishDate;
    }*/

    public OffsetDateTime getPublishDate() {
        return FormatUtils.string2ODT(publishDate);
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public BaseListItemBean clone() {
        BaseListItemBean o = null;
        try {
            o = (BaseListItemBean) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        return o;
    }
}