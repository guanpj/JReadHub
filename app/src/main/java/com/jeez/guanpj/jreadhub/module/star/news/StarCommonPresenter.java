package com.jeez.guanpj.jreadhub.module.star.news;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.data.DataManager;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class StarCommonPresenter extends BasePresenter<StarCommonContract.View> implements StarCommonContract.Presenter {

    private DataManager mDataManager;

    @Inject
    StarCommonPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttatch(StarCommonContract.View view) {
        super.onAttatch(view);
        initEvent();
    }

    private void initEvent() {
        addSubscribe(RxBus.getInstance().toFlowable(FabClickEvent.class)
                .subscribe(fabClickEvent -> getView().onFabClick(fabClickEvent.getCurrentItemIndex())));
    }

    @Override
    public void doRefresh(boolean isPullToRefresh) {
        addSubscribe(mDataManager.getAllNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
