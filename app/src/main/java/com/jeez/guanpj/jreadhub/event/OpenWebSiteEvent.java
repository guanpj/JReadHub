package com.jeez.guanpj.jreadhub.event;

public class OpenWebSiteEvent {

    private String url;

    public OpenWebSiteEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
