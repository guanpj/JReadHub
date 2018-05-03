package com.jeez.guanpj.jreadhub.ui.topic.detail;

import android.os.AsyncTask;

import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicTraceBean;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxSchedulers;
import com.jeez.guanpj.jreadhub.util.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class TopicDetailPresenter extends BasePresenter<TopicDetailContract.View> implements TopicDetailContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public TopicDetailPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
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

    public void getTopicTrace(final String topicId) {
        new TopicTraceAsyncTask(topicId, this).execute();
    }

    public class TopicTraceAsyncTask extends AsyncTask<Void, Void, Document> {
        private String mTopicId;
        private TopicDetailPresenter mPresenter;

        public TopicTraceAsyncTask(String topicId, TopicDetailPresenter presenter) {
            mTopicId = topicId;
            mPresenter = presenter;
        }

        @Override protected Document doInBackground(Void... params) {
            Document document = null;
            try {
                document = Jsoup.connect(Constants.TOPIC_DETAIL_URL + mTopicId).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return document;
        }

        @Override protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            if (document == null) return;
            Elements timelineContainer = document.getElementsByClass(
                    "timeline__container___3jHS8 timeline__container--PC___1D1r7");
            if (timelineContainer == null || timelineContainer.select("li").isEmpty()) return;
            List<TopicTraceBean> traceBeans = new ArrayList<>();
            for (Element liElement : timelineContainer.select("li")) {
                TopicTraceBean topicTraceBean = new TopicTraceBean();
                topicTraceBean.date = liElement.getElementsByClass("date-item___1io1R").text();
                Element contentElement = liElement.getElementsByClass("content-item___3KfMq").get(0);
                topicTraceBean.content = contentElement.getElementsByTag("a").get(0).text();
                topicTraceBean.url = contentElement.getElementsByTag("a").get(0).attr("href");
                traceBeans.add(topicTraceBean);
            }
            if (mPresenter.getView() != null) {
                mPresenter.getView().onRequestTopicTraceEnd(traceBeans);
            }
            mPresenter = null;
        }
    }
}
