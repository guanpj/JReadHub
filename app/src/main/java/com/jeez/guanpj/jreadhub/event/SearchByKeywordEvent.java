package com.jeez.guanpj.jreadhub.event;

public class SearchByKeywordEvent {
    private String mKeyword;

    public SearchByKeywordEvent(String mKeyword) {
        this.mKeyword = mKeyword;
    }

    public String getKeyword() {
        return mKeyword;
    }

    public void setmKeyword(String mKeyword) {
        this.mKeyword = mKeyword;
    }
}
