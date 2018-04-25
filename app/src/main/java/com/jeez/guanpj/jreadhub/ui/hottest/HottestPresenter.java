package com.jeez.guanpj.jreadhub.ui.hottest;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;
import com.jeez.guanpj.jreadhub.ui.hottest.HottestContract.Presenter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class HottestPresenter extends BasePresenter<HottestContract.View> implements Presenter {
    private DataManager mDataManager;
    private static final int PAGE_SIZE = 20;

    @Inject
    HottestPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    private DisposableObserver<DataListBean<TopicBean>> mObserber = new DisposableObserver<DataListBean<TopicBean>>() {
        @Override
        public void onNext(DataListBean<TopicBean> topicBeanDataListBean) {
            getView().onRequestEnd(topicBeanDataListBean, false);
        }

        @Override
        public void onError(Throwable e) {
            getView().showError();
        }

        @Override
        public void onComplete() {
            getView().showContent();
        }
    };

    @Override
    public void doRefresh() {
        mRxManager.add(mDataManager.getTopicList(null, PAGE_SIZE)
                .compose(RxSchedulers.io2Main())
                .doOnSubscribe(disposable -> getView().showLoading(true))
                .subscribeWith(new DisposableObserver<DataListBean<TopicBean>>() {
                    @Override
                    public void onNext(DataListBean<TopicBean> topicBeanDataListBean) {
                        getView().onRequestEnd(topicBeanDataListBean, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onRequestError(true);
                    }

                    @Override
                    public void onComplete() {
                        getView().showContent();
                    }
                }));
    }

    @Override
    public void doLoadMore(Long lastCursor) {
        mRxManager.add(mDataManager.getTopicList(lastCursor, PAGE_SIZE)
                .compose(RxSchedulers.io2Main())
                .doOnSubscribe(disposable -> getView().showLoading(false))
                .subscribeWith(new DisposableObserver<DataListBean<TopicBean>>() {
                    @Override
                    public void onNext(DataListBean<TopicBean> topicBeanDataListBean) {
                        getView().onRequestEnd(topicBeanDataListBean, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onRequestError(false);
                    }

                    @Override
                    public void onComplete() {
                        getView().showContent();
                    }
                }));
    }
}
