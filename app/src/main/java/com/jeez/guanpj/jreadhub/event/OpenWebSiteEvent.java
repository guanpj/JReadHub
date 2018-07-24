package com.jeez.guanpj.jreadhub.event;

import com.jeez.guanpj.jreadhub.bean.NewsBean;

public class OpenWebSiteEvent {

    private NewsBean newsBean;
    private String url;
    private String title;

    public OpenWebSiteEvent(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public OpenWebSiteEvent(NewsBean newsBean) {
        this.newsBean = newsBean;
    }

    public NewsBean getNewsBean() {
        return newsBean;
    }

    public void setNewsBean(NewsBean newsBean) {
        this.newsBean = newsBean;
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

}
