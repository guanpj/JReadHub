package com.jeez.guanpj.jreadhub.module.common;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.data.DataManager;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.module.adpter.DiffCallback;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;
import com.jeez.guanpj.jreadhub.util.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class CommonPresenter extends BasePresenter<CommonContract.View> implements CommonContract.Presenter {
    private DataManager mDataManager;

    @Inject
    CommonPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttach(CommonContract.View view) {
        super.onAttach(view);
        initEvent();
    }

    private void initEvent() {
        addSubscribe(RxBus.getInstance().toFlowable(FabClickEvent.class)
                .subscribe(fabClickEvent -> getView().onFabClick(fabClickEvent.getCurrentItemIndex())));
    }

    @Override
    public void doRefresh(@Constants.Type String type, boolean isPullToRefresh) {
        addSubscribe(mDataManager.getNewsList(type, null, Constants.TOPIC_PAGE_SIZE)
                .compose(RxSchedulers.observableIo2Main())
                .doOnSubscribe(disposable -> getView().showLoading(isPullToRefresh))
                .subscribeWith(new DisposableObserver<DataListBean<NewsBean>>() {
                    @Override
                    public void onNext(DataListBean<NewsBean> newsBeanDataListBean) {
                        getView().bindData(newsBeanDataListBean, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError();
                    }

                    @Override
                    public void onComplete() {
                        getView().showContent();
                    }
                }));
    }

    @Override
    public void doLoadMore(@Constants.Type String type, Long lastCursor) {
        addSubscribe(mDataManager.getNewsList(type, lastCursor, Constants.TOPIC_PAGE_SIZE)
                .compose(RxSchedulers.observableIo2Main())
                .subscribeWith(new DisposableObserver<DataListBean<NewsBean>>() {
                    @Override
                    public void onNext(DataListBean<NewsBean> newsBeanDataListBean) {
                        getView().bindData(newsBeanDataListBean, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().bindData(null, false);
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @Override
    public void getDiffResult(List<NewsBean> oldData, List<NewsBean> newData) {
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
