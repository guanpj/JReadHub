package com.jeez.guanpj.jreadhub.module.web;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.data.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class WebViewPresenter extends BasePresenter<WebViewContract.View> implements WebViewContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public WebViewPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void checkStar(String newsId) {
        addSubscribe(mDataManager.getNewsById(newsId)
                .compose(RxSchedulers.flowableIo2Main())
                .subscribeWith(new DisposableSubscriber<List<NewsBean>>() {
                    @Override
                    public void onNext(List<NewsBean> topicBean) {
                        if (null != topicBean && topicBean.size() > 0) {
                            getView().onCheckStarResult(true);
                        } else {
                            getView().onCheckStarResult(false);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        /*mDataManager.getSingleBean(NewsBean.class, newsId)
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
                            getView().onCheckStarResult(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof EmptyResultSetException) {
                            getView().onCheckStarResult(false);
                        }
                    }
                });*/
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
