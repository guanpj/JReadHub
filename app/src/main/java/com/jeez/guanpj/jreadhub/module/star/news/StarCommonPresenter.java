package com.jeez.guanpj.jreadhub.module.star.news;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.data.DataManager;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

public class StarCommonPresenter extends BasePresenter<StarCommonContract.View> implements StarCommonContract.Presenter {

    private DataManager mDataManager;

    @Inject
    StarCommonPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttach(StarCommonContract.View view) {
        super.onAttach(view);
        initEvent();
    }

    private void initEvent() {
        addSubscribe(RxBus.getInstance().toFlowable(FabClickEvent.class)
                .subscribe(fabClickEvent -> getView().onFabClick(fabClickEvent.getCurrentItemIndex())));
    }

    @Override
    public void getDataWithKeyword(String keyWord) {
        addSubscribe(mDataManager.getNewsByKeyword(keyWord)
                .compose(RxSchedulers.flowableIo2Main())
                .doOnSubscribe(disposable -> getView().showLoading(false))
                .subscribeWith(new DisposableSubscriber<List<NewsBean>>() {
                    @Override
                    public void onNext(List<NewsBean> topicBeans) {
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
    public void doRefresh(boolean isPullToRefresh) {
        addSubscribe(mDataManager.getAllNews()
                .compose(RxSchedulers.flowableIo2Main())
                .doOnSubscribe(disposable -> getView().showLoading(isPullToRefresh))
                .subscribeWith(new DisposableSubscriber<List<NewsBean>>() {
                    @Override
                    public void onNext(List<NewsBean> topicBeans) {
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
