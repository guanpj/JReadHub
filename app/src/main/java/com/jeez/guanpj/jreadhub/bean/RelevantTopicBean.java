package com.jeez.guanpj.jreadhub.bean;

import com.jeez.guanpj.jreadhub.util.FormatUtils;

import org.threeten.bp.OffsetDateTime;

public class RelevantTopicBean {
    String createdAt;
    String id;
    String title;
    String url;
    String mobileUrl;

    public OffsetDateTime getCreatedAt() {
        return FormatUtils.string2ODT(createdAt);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }
}
