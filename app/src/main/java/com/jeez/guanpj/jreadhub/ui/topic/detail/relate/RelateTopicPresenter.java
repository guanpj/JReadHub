package com.jeez.guanpj.jreadhub.ui.topic.detail.relate;

import com.jeez.guanpj.jreadhub.bean.RelateTopicBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class RelateTopicPresenter extends BasePresenter<RelateTopicContract.View> implements RelateTopicContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public RelateTopicPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getRelateTopic(String topicId, int eventType, long order, long timeStamp) {
        addSubscribe(mDataManager.getRelateTopic(topicId, eventType, order, timeStamp)
                .compose(RxSchedulers.io2Main())
                .doOnSubscribe(disposable -> getView().onRequestStart())
                .subscribeWith(new DisposableObserver<RelateTopicBean>() {
                    @Override
                    public void onNext(RelateTopicBean bean) {
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
