package com.jeez.guanpj.jreadhub.module.web;

import android.arch.persistence.room.EmptyResultSetException;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WebViewPresenter extends BasePresenter<WebViewContract.View> implements WebViewContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public WebViewPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void checkStar(String newsId, boolean showTips) {
        mDataManager.getSingleBean(NewsBean.class, newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onSuccess(NewsBean newsBean) {
                        if (null != newsBean) {
                            getView().onCheckStarResult(true, showTips);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof EmptyResultSetException) {
                            getView().onCheckStarResult(false, showTips);
                        }
                    }
                });
    }

    @Override
    public void addStar(NewsBean newsBean) {
        mDataManager.insert(newsBean);
    }

    @Override
    public void removeStar(NewsBean newsBean) {
        mDataManager.delete(newsBean);
    }
}
