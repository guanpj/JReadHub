package com.jeez.guanpj.jreadhub.ui.topic.detail;

import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class TopicDetailPresenter extends BasePresenter<TopicDetailContract.View> implements TopicDetailContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public TopicDetailPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void getTopicDetail(String topicId) {
        addSubscribe(mDataManager.getTopicDetail(topicId)
                .compose(RxSchedulers.io2Main())
                .doOnSubscribe(disposable -> getView().onRequestStart())
                .subscribeWith(new DisposableObserver<TopicBean>() {
                    @Override
                    public void onNext(TopicBean bean) {
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
