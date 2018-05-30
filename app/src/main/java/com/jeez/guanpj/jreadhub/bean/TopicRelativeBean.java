package com.jeez.guanpj.jreadhub.bean;

import com.jeez.guanpj.jreadhub.util.FormatUtils;

import org.parceler.Parcel;
import org.threeten.bp.OffsetDateTime;

public class TopicRelativeBean {
    String createdAt;
    String id;
    String title;

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
}
