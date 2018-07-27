package com.jeez.guanpj.jreadhub.bean;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

public class BaseListItemBean implements Cloneable {

    @PrimaryKey @NonNull
    protected String id;
    protected String title;

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

    @Override
    public java.lang.Object clone() {
        java.lang.Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
        }
        return o;
    }
}
