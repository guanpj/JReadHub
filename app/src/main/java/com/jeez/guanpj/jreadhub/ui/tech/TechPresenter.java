package com.jeez.guanpj.jreadhub.ui.tech;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class TechPresenter extends BasePresenter<TechContract.View> implements TechContract.Presenter {
    private DataManager mDataManager;
    private static final int PAGE_SIZE = 20;

    @Inject
    TechPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void doRefresh() {
        addSubscribe(mDataManager.getNewsList(NewsBean.TYPE_NEWS, null, PAGE_SIZE)
                .compose(RxSchedulers.io2Main())
                .subscribeWith(new DisposableObserver<DataListBean<NewsBean>>() {
                    @Override
                    public void onNext(DataListBean<NewsBean> newsBeanDataListBean) {
                        getView().onRequestEnd(newsBeanDataListBean, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onRequestError(true);
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @Override
    public void doLoadMore(Long lastCursor) {
        addSubscribe(mDataManager.getNewsList(NewsBean.TYPE_NEWS, lastCursor, PAGE_SIZE)
                .compose(RxSchedulers.io2Main())
                .subscribeWith(new DisposableObserver<DataListBean<NewsBean>>() {
                    @Override
                    public void onNext(DataListBean<NewsBean> newsBeanDataListBean) {
                        getView().onRequestEnd(newsBeanDataListBean, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onRequestError(false);
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }
}
