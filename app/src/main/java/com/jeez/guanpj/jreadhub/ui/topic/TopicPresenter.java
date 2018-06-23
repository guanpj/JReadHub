package com.jeez.guanpj.jreadhub.ui.topic;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewTopicCountBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class TopicPresenter extends BasePresenter<TopicContract.View> implements TopicContract.Presenter {
    private DataManager mDataManager;
    private static final int PAGE_SIZE = 20;

    @Inject
    TopicPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
        initEvent();
    }

    private void initEvent() {
        addSubscribe(RxBus.getInstance().toFlowable(FabClickEvent.class)
                .subscribe(fabClickEvent -> getView().onFabClick()));
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

    @Override
    public void getNewTopicCount(Long latestCursor) {
        addSubscribe(mDataManager.getNewTopicCount(latestCursor)
                .compose(RxSchedulers.io2Main())
                .subscribeWith(new DisposableObserver<NewTopicCountBean>() {
                    @Override
                    public void onNext(NewTopicCountBean newTopicCountBean) {
                        if (newTopicCountBean.getCount() > 0) {
                            getView().showNewTopicCount(newTopicCountBean.getCount());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
