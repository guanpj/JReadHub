package com.jeez.guanpj.jreadhub.ui.topic;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewTopicCountBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;
import com.jeez.guanpj.jreadhub.ui.adpter.DiffCallback;
import com.jeez.guanpj.jreadhub.util.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class TopicPresenter extends BasePresenter<TopicContract.View> implements TopicContract.Presenter {
    private DataManager mDataManager;

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
        addSubscribe(mDataManager.getTopicList(null, Constants.TOPIC_PAGE_SIZE)
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
        addSubscribe(mDataManager.getTopicList(lastCursor, Constants.TOPIC_PAGE_SIZE)
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
                        //返回的置顶话题个数包括置顶的话题
                        if (newTopicCountBean.getCount() > Constants.TOPIC_TOP_COUNT) {
                            getView().showNewTopicCount(newTopicCountBean.getCount() - Constants.TOPIC_TOP_COUNT);
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

    @Override
    public void getDiffResult(List<TopicBean> oldData, List<TopicBean> newData) {
        addSubscribe(Observable.just(DiffUtil.calculateDiff(new DiffCallback(oldData, newData), false))
                .subscribeWith(new DisposableObserver<DiffUtil.DiffResult>() {
                    @Override
                    public void onNext(DiffUtil.DiffResult diffResult) {
                        getView().onDiffResult(diffResult, newData);
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
