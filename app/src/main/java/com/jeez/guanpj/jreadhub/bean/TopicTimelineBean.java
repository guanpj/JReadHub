package com.jeez.guanpj.jreadhub.bean;

import java.util.ArrayList;

public class TopicTimelineBean {
    private int errorCode;
    private ArrayList<RelevantTopicBean> topics;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<RelevantTopicBean> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<RelevantTopicBean> topics) {
        this.topics = topics;
    }
}
