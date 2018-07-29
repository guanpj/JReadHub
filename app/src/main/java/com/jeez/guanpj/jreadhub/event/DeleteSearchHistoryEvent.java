package com.jeez.guanpj.jreadhub.event;

import com.jeez.guanpj.jreadhub.bean.SearchHistoryBean;

public class DeleteSearchHistoryEvent {

    private SearchHistoryBean searchHistoryBean;

    public DeleteSearchHistoryEvent(SearchHistoryBean searchHistoryBean) {
        this.searchHistoryBean = searchHistoryBean;
    }

    public SearchHistoryBean getSearchHistoryBean() {
        return searchHistoryBean;
    }

    public void setSearchHistoryBean(SearchHistoryBean searchHistoryBean) {
        this.searchHistoryBean = searchHistoryBean;
    }
}
