package com.jeez.guanpj.jreadhub.bean;

import java.util.ArrayList;

public class TopicTimelineBean {
    int errorCode;
    ArrayList<RelateTopicBean> topics;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<RelateTopicBean> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<RelateTopicBean> topics) {
        this.topics = topics;
    }
}
