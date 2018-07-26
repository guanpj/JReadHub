package com.jeez.guanpj.jreadhub.bean;

import android.arch.persistence.room.Entity;

import com.jeez.guanpj.jreadhub.util.FormatUtils;

import org.threeten.bp.OffsetDateTime;

import java.io.Serializable;

@Entity(tableName = "news")
public class NewsBean extends BaseListItemBean implements Serializable {

    private String authorName;
    private String language;
    private String mobileUrl;
    private String publishDate;
    private String siteName;
    private String siteSlug;
    private String summary;
    private String summaryAuto;
    private String url;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public OffsetDateTime getFormattedPublishDate() {
        return FormatUtils.string2ODT(publishDate);
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummaryAuto() {
        return summaryAuto;
    }

    public void setSummaryAuto(String summaryAuto) {
        this.summaryAuto = summaryAuto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
