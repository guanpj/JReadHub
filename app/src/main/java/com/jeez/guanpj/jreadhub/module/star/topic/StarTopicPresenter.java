package com.jeez.guanpj.jreadhub.module.star.topic;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewTopicCountBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.module.adpter.DiffCallback;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;
import com.jeez.guanpj.jreadhub.util.Constants;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class StarTopicPresenter extends BasePresenter<StarTopicContract.View> implements StarTopicContract.Presenter {

    private DataManager mDataManager;

    @Inject
    StarTopicPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttatch(StarTopicContract.View view) {
        super.onAttatch(view);
        initEvent();
    }

    private void initEvent() {
        addSubscribe(RxBus.getInstance().toFlowable(FabClickEvent.class)
                .filter(fabClickEvent -> fabClickEvent.getCurrentItemIndex() == 0)
                .subscribe(fabClickEvent -> getView().onFabClick()));
    }

    @Override
    public void doRefresh(boolean isPullToRefresh) {
        addSubscribe(mDataManager.getAllTopic2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getView().showLoading(isPullToRefresh))
                .subscribe(new Consumer<List<TopicBean>>() {
                    @Override
                    public void accept(List<TopicBean> topicBeans) throws Exception {
                        getView().bindData(topicBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().showError();
                    }
                }));
        /*addSubscribe(mDataManager.getAllTopic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getView().showLoading(isPullToRefresh))
                .subscribeWith(new DisposableSubscriber<List<TopicBean>>() {
                    @Override
                    public void onNext(List<TopicBean> topicBeanDataListBean) {
                        getView().bindData(topicBeanDataListBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError();
                    }

                    @Override
                    public void onComplete() {
                        getView().showContent();
                    }
                }));*/
    }

    @Override
    public void doLoadMore(Long lastCursor) {

    }
}
