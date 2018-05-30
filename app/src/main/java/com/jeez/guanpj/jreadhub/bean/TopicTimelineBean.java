package com.jeez.guanpj.jreadhub.bean;

import org.parceler.Parcel;

import java.util.ArrayList;

public class TopicTimelineBean {
    int errorCode;
    ArrayList<TopicRelativeBean> topics;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<TopicRelativeBean> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<TopicRelativeBean> topics) {
        this.topics = topics;
    }
}
