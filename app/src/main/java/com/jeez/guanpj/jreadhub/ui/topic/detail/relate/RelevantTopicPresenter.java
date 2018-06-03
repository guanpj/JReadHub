package com.jeez.guanpj.jreadhub.ui.topic.detail.relate;

import com.jeez.guanpj.jreadhub.bean.RelevantTopicBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class RelevantTopicPresenter extends BasePresenter<RelevantTopicContract.View> implements RelevantTopicContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public RelevantTopicPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getRelateTopic(String topicId, int eventType, long order, long timeStamp) {
        addSubscribe(mDataManager.getRelateTopic(topicId, eventType, order, timeStamp)
                .compose(RxSchedulers.io2Main())
                .doOnSubscribe(disposable -> getView().onRequestStart())
                .subscribeWith(new DisposableObserver<RelevantTopicBean>() {
                    @Override
                    public void onNext(RelevantTopicBean bean) {
                        getView().onRequestTopicEnd(bean);
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
