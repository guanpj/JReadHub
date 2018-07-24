package com.jeez.guanpj.jreadhub.module.topic;

import android.support.v7.util.DiffUtil;

import com.jeez.guanpj.jreadhub.bean.DataListBean;
import com.jeez.guanpj.jreadhub.bean.NewTopicCountBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
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

public class TopicPresenter extends BasePresenter<TopicContract.View> implements TopicContract.Presenter {

    private DataManager mDataManager;

    @Inject
    TopicPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttatch(TopicContract.View view) {
        super.onAttatch(view);
        initEvent();
    }

    private void initEvent() {
        addSubscribe(RxBus.getInstance().toFlowable(FabClickEvent.class)
                .filter(fabClickEvent -> fabClickEvent.getCurrentItemIndex() == 0)
                .subscribe(fabClickEvent -> getView().onFabClick()));
    }

    @Override
    public void doRefresh(boolean isPullToRefresh) {
        addSubscribe(mDataManager.getTopicList(null, Constants.TOPIC_PAGE_SIZE)
                .compose(RxSchedulers.io2Main())
                .doOnSubscribe(disposable -> getView().showLoading(isPullToRefresh))
                .subscribeWith(new DisposableObserver<DataListBean<TopicBean>>() {
                    @Override
                    public void onNext(DataListBean<TopicBean> topicBeanDataListBean) {
                        //测试
//                        if (isPullToRefresh) {
//                            TopicBean newTopic = new TopicBean();
//                            newTopic.setId("1mY1Lpcntvs");
//                            newTopic.setTitle("华为P20获EMUI新版更新：提升吃鸡流畅性");
//                            newTopic.setCreatedAt("2018-04-25T02:55:26.234Z");
//                            newTopic.setOrder(46115);
//                            newTopic.setPublishDate("2018-04-25T02:55:26.263Z");
//                            newTopic.setSummary("4月24日，华为向P20推送EMUI新版更新，下面我们来详细了解下 ... 本次更新优化了相机和音效的使用体验，");
//                            newTopic.setUpdatedAt("2018-04-25T02:55:26.942Z");
//                            TopicBean.Extra extra = new TopicBean.Extra();
//                            extra.instantView = true;
//                            newTopic.setExtra(extra);
//
//                            TopicNewsBean topicNewsBean = new TopicNewsBean();
//                            topicNewsBean.setId(19116115);
//                            topicNewsBean.setUrl("http://tech.ifeng.com/a/20180424/44967717_0.shtml");
//                            topicNewsBean.setTitle("华为P20获EMUI新版更新：提升吃鸡流畅性");
//                            topicNewsBean.setSiteName("凤凰科技");
//                            topicNewsBean.setSiteSlug("site_ifeng");
//                            topicNewsBean.setMobileUrl("http://m.ifeng.com/sharenews.f?ch=qd_sdk_dl1&aid=040590044967717");
//                            topicNewsBean.setAuthorName("凤凰号");
//                            topicNewsBean.setPublishDate("2018-04-24T15:40:07.000Z");
//
//                            ArrayList<TopicNewsBean> list = new ArrayList<>();
//                            list.add(topicNewsBean);
//
//                            newTopic.setNewsArray(list);
//
//                            ArrayList<TopicBean> topicBeans = (ArrayList<TopicBean>) ((ArrayList)(topicBeanDataListBean.getData())).clone();
//                            topicBeans.remove(0);
//                            topicBeans.add(0, newTopic);
//
//                            topicBeanDataListBean.setData(topicBeans);
//                        }
                        getView().bindData(topicBeanDataListBean, true);
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
    public void doLoadMore(Long lastCursor) {
        addSubscribe(mDataManager.getTopicList(lastCursor, Constants.TOPIC_PAGE_SIZE)
                .compose(RxSchedulers.io2Main())
                .subscribeWith(new DisposableObserver<DataListBean<TopicBean>>() {
                    @Override
                    public void onNext(DataListBean<TopicBean> topicBeanDataListBean) {
                        getView().bindData(topicBeanDataListBean, false);
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
    public void getNewTopicCount(Long latestCursor) {
        addSubscribe(mDataManager.getNewTopicCount(latestCursor)
                .compose(RxSchedulers.io2Main())
                .subscribeWith(new DisposableObserver<NewTopicCountBean>() {
                    @Override
                    public void onNext(NewTopicCountBean newTopicCountBean) {
                        //返回的置顶话题个数包括置顶的话题
                        if (newTopicCountBean.getCount() > Constants.TOPIC_TOP_COUNT) {
                            getView().showNewTopicCount(newTopicCountBean.getCount() - Constants.TOPIC_TOP_COUNT);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void getDiffResult(List<TopicBean> oldData, List<TopicBean> newData) {
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
