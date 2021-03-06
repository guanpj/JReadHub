package com.jeez.guanpj.jreadhub.module.topic.detail.relate;

import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.data.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class RelevantTopicPresenter extends BasePresenter<RelevantTopicContract.View> implements RelevantTopicContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public RelevantTopicPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void getRelateTopic(String topicId, int eventType, long order, long timeStamp) {
        addSubscribe(mDataManager.getRelateTopic(topicId, eventType, order, timeStamp)
                .compose(RxSchedulers.observableIo2Main())
                .doOnSubscribe(disposable -> getView().onRequestStart())
                .subscribeWith(new DisposableObserver<List<RelevantTopicBean>>() {
                    @Override
                    public void onNext(List<RelevantTopicBean> list) {
                        getView().onRequestTopicEnd(list);
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
