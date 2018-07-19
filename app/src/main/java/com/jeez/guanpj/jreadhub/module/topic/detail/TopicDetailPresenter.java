package com.jeez.guanpj.jreadhub.module.topic.detail;

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
    public void getTopicDetail(String topicId, boolean isPullToRefresh) {
        addSubscribe(mDataManager.getTopicDetail(topicId)
                .compose(RxSchedulers.io2Main())
                .doOnSubscribe(disposable -> getView().showLoading(isPullToRefresh))
                .subscribeWith(new DisposableObserver<TopicBean>() {
                    @Override
                    public void onNext(TopicBean bean) {
                        getView().bindData(bean);
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
    public void addStar(TopicBean topicBean) {
        mDataManager.insert(topicBean);
    }

    @Override
    public void removeStar(TopicBean topicBean) {
        mDataManager.delete(topicBean);
    }
}
