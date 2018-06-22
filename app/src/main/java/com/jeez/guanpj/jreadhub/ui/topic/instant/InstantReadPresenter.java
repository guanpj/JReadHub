package com.jeez.guanpj.jreadhub.ui.topic.instant;

import com.jeez.guanpj.jreadhub.bean.InstantReadBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class InstantReadPresenter extends BasePresenter<InstantReadContract.View> implements InstantReadContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public InstantReadPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void getTopicInstantRead(String topicId) {
        addSubscribe(mDataManager.getTopicInstantRead(topicId)
                .compose(RxSchedulers.io2Main())
                .doOnSubscribe(disposable -> getView().onRequestStart())
                .subscribeWith(new DisposableObserver<InstantReadBean>() {
                    @Override
                    public void onNext(InstantReadBean instantReadBean) {
                        getView().onRequestEnd(instantReadBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onRequestError();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
