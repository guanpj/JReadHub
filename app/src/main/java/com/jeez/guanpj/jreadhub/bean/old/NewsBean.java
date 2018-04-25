package com.jeez.guanpj.jreadhub.bean.old;

import android.support.annotation.StringDef;

import org.threeten.bp.OffsetDateTime;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class NewsBean {

    public static final String TYPE_NEWS = "news";
    public static final String TYPE_TECHNEWS = "technews";
    public static final String TYPE_BLOCKCHAIN = "blockchain";
    public static final String TYPE_JOBS = "jobs";

    @StringDef({TYPE_NEWS, TYPE_TECHNEWS, TYPE_BLOCKCHAIN, TYPE_JOBS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {}

    private long id;

    private String title;

    private String summary;

    private String summaryAuto;

    private String url;

    private String mobileUrl;

    private String siteName;

    private String language;

    private String authorName;

    private OffsetDateTime publishDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public OffsetDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(OffsetDateTime publishDate) {
        this.publishDate = publishDate;
    }

}
