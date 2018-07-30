package com.jeez.guanpj.jreadhub.module.star.search;

import android.database.Cursor;

import com.jeez.guanpj.jreadhub.bean.SearchHistoryBean;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.IBasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseMvpView;

import java.util.List;

public interface SearchContract {
    interface View extends IBaseMvpView {
        void bindData(List<SearchHistoryBean> data);

        void showEmpty();

        void onHistoryCursorResult(Cursor cursor);
    }

    interface Presenter extends IBasePresenter<View> {
        void loadAllHistory();

        void addHistory(SearchHistoryBean searchHistoryBean);

        void getHistoryCursor(String keyWord);

        void updateHistory(SearchHistoryBean searchHistoryBean);

        void deleteHistory(SearchHistoryBean searchHistoryBean);
    }
}
