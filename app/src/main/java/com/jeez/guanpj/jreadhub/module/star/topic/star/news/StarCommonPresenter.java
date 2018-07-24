package com.jeez.guanpj.jreadhub.module.star.topic.star.news;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
                .subscribe(new Consumer<List<NewsBean>>() {
                    @Override
                    public void accept(List<NewsBean> topicBeans) throws Exception {
                        getView().bindData(topicBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().showEmpty();
                    }
                }));
    }

    @Override
    public void doLoadMore(Long lastCursor) {
    }
}
