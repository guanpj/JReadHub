package com.jeez.guanpj.jreadhub.module.star.topic;

import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class StarTopicPresenter extends BasePresenter<StarTopicContract.View> implements StarTopicContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public StarTopicPresenter(DataManager dataManager) {
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
        addSubscribe(mDataManager.getAllTopic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getView().showLoading(isPullToRefresh))
                .subscribeWith(new DisposableSubscriber<List<TopicBean>>() {
                    @Override
                    public void onNext(List<TopicBean> topicBeans) {
                        if (null != topicBeans && !topicBeans.isEmpty()) {
                            getView().bindData(topicBeans, true);
                            getView().showContent();
                        } else {
                            getView().showEmpty();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        getView().showError();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @Override
    public void doLoadMore(Long lastCursor) {
        getView().bindData(null, false);
    }
}
