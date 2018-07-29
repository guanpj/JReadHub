package com.jeez.guanpj.jreadhub.module.star.search;

import android.arch.persistence.room.EmptyResultSetException;

import com.jeez.guanpj.jreadhub.bean.SearchHistoryBean;
import com.jeez.guanpj.jreadhub.data.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public SearchPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void loadAllHistory() {
        addSubscribe(mDataManager.getAllSearchHistroy()
                .compose(RxSchedulers.flowableIo2Main())
                .subscribeWith(new DisposableSubscriber<List<SearchHistoryBean>>() {
                    @Override
                    public void onNext(List<SearchHistoryBean> searchHistoryBeans) {
                        if (searchHistoryBeans != null && !searchHistoryBeans.isEmpty()) {
                            getView().bindData(searchHistoryBeans);
                        } else {
                            getView().showEmpty();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @Override
    public void addHistory(SearchHistoryBean newDataBean) {
        mDataManager.getSingleHistory(newDataBean.getKeyWord())
                .compose(RxSchedulers.singleIo2Main())
                .subscribe(new SingleObserver<SearchHistoryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onSuccess(SearchHistoryBean oldDataBean) {
                        oldDataBean.setTime(newDataBean.getTime());
                        updateHistory(oldDataBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof EmptyResultSetException) {
                            mDataManager.insert(newDataBean);
                        }
                    }
                });
    }

    @Override
    public void updateHistory(SearchHistoryBean searchHistoryBean) {
        mDataManager.update(searchHistoryBean);
    }

    @Override
    public void deleteHistory(SearchHistoryBean searchHistoryBean) {
        mDataManager.delete(searchHistoryBean);
    }
}
