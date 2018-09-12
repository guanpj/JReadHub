package com.jeez.guanpj.jreadhub.module.topic.detail;

import com.jeez.guanpj.jreadhub.bean.TopicDetailBean;
import com.jeez.guanpj.jreadhub.data.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

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
                .compose(RxSchedulers.observableIo2Main())
                .doOnSubscribe(disposable -> getView().showLoading(isPullToRefresh))
                .subscribeWith(new DisposableObserver<TopicDetailBean>() {
                    @Override
                    public void onNext(TopicDetailBean bean) {
                        getView().bindData(bean, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        getView().showContent();
                    }
                }));
    }

    @Override
    public void checkStar(String topicId) {
        addSubscribe(mDataManager.getTopicById(topicId)
                .compose(RxSchedulers.flowableIo2Main())
                .subscribeWith(new DisposableSubscriber<List<TopicDetailBean>>() {
                    @Override
                    public void onNext(List<TopicDetailBean> topicBean) {
                        if (null != topicBean && topicBean.size() > 0) {
                            getView().onCheckStarResult(true);
                        } else {
                            getView().onCheckStarResult(false);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        /*mDataManager.getSingleBean(TopicBean.class, topicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<TopicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onSuccess(TopicBean topicBean) {
                        if (null != topicBean) {
                            getView().onCheckStarResult(true, showTips);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof EmptyResultSetException) {
                            getView().onCheckStarResult(false, showTips);
                        }
                    }
                });*/
    }

    @Override
    public void addStar(TopicDetailBean topicBean) {
        mDataManager.insert(topicBean);
    }

    @Override
    public void removeStar(TopicDetailBean topicBean) {
        mDataManager.delete(topicBean);
    }
}
