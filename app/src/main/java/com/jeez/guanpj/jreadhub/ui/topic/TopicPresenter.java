package com.jeez.guanpj.jreadhub.ui.topic;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class TopicPresenter extends BasePresenter<TopicContract.View> implements TopicContract.Presenter {
    private DataManager mDataManager;
    private static final int PAGE_SIZE = 20;

    @Inject
    TopicPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void doRefresh() {
        addSubscribe(mDataManager.getTopicList(null, PAGE_SIZE)
                .compose(RxSchedulers.io2Main())
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
                    }
                }));
    }

    @Override
    public void doLoadMore(Long lastCursor) {
        addSubscribe(mDataManager.getTopicList(lastCursor, PAGE_SIZE)
                .compose(RxSchedulers.io2Main())
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
                    }
                }));
    }
}
