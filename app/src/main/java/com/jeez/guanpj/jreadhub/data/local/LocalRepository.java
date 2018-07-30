package com.jeez.guanpj.jreadhub.data.local;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.SearchHistoryBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.data.local.dao.NewsDao;
import com.jeez.guanpj.jreadhub.data.local.dao.SearchHistoryDao;
import com.jeez.guanpj.jreadhub.data.local.dao.TopicDao;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class LocalRepository implements LocalDataSource {

    private NewsDao mNewsDao;
    private TopicDao mTopicDao;
    private SearchHistoryDao mSearchHistoryDao;
    private ExecutorService mExecutorService;

    @Inject
    public LocalRepository(TopicDao topicDao, NewsDao newsDao, SearchHistoryDao searchHistoryDao, ExecutorService executorService) {
        mTopicDao = topicDao;
        mNewsDao = newsDao;
        mSearchHistoryDao = searchHistoryDao;
        mExecutorService = executorService;
    }

    @Override
    public Flowable<List<TopicBean>> getTopicById(String id) {
        return mTopicDao.getTopicById(id);
    }

    @Override
    public Flowable<List<NewsBean>> getNewsById(String id) {
        return mNewsDao.getNewsById(id);
    }

    @Override
    public <T> Single<T> getSingleBean(Class<T> tClass, String id) {
        if (TopicBean.class.equals(tClass)) {
            return (Single<T>) mTopicDao.getSingleTopicById(id);
        } else if (NewsBean.class.equals(tClass)) {
            return (Single<T>) mNewsDao.getSingleNewsById(id);
        }
        return null;
    }

    @Override
    public Flowable<List<TopicBean>> getTopicsByKeyword(@NonNull String keyWord) {
        return mTopicDao.getTopicsByKeyword(keyWord);
    }

    @Override
    public Flowable<List<NewsBean>> getNewsByKeyword(@NonNull String keyWord) {
        return mNewsDao.getNewsByKeyword(keyWord);
    }

    @Override
    public Flowable<List<TopicBean>> getAllTopic() {
        return  mTopicDao.getAllTopic();
    }

    @Override
    public Flowable<List<NewsBean>> getAllNews() {
        return  mNewsDao.getAllNews();
    }

    @Override
    public Flowable<List<SearchHistoryBean>> getAllSearchHistroy() {
        return mSearchHistoryDao.getAllHistory();
    }

    @Override
    public Single<SearchHistoryBean> getSingleHistory(@NonNull String keyWord) {
        return mSearchHistoryDao.getSingleHistory(keyWord);
    }

    @Override
    public <T> void deleteAll(@NonNull Class<T> tClass) {
        mExecutorService.execute(() -> {
            if (TopicBean.class.equals(tClass)) {
                mTopicDao.deleteAllTopic();
            } else if (NewsBean.class.equals(tClass)) {
                mNewsDao.deleteAllNews();
            } else if (SearchHistoryBean.class.equals(tClass)) {
                mSearchHistoryDao.deleteAllHistory();
            }
        });
    }

    @Override
    public Cursor getHistoryCursor(@NonNull String keyWord) {
        try {
            return mExecutorService.submit(() -> mSearchHistoryDao.getHistoryCursor(keyWord)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(@NonNull Object object) {
        mExecutorService.execute(() -> {
            if (object instanceof TopicBean) {
                mTopicDao.deleteTopic((TopicBean) object);
            } else if (object instanceof NewsBean) {
                mNewsDao.deleteNews((NewsBean) object);
            } else if (object instanceof SearchHistoryBean) {
                mSearchHistoryDao.deleteHistory((SearchHistoryBean) object);
            }
        });
    }

    @Override
    public void insert(@NonNull Object object) {
        mExecutorService.execute(() -> {
            if (object instanceof TopicBean) {
                mTopicDao.insertTopic((TopicBean) object);
            } else if (object instanceof NewsBean) {
                mNewsDao.insertNews((NewsBean) object);
            } else if (object instanceof SearchHistoryBean) {
                mSearchHistoryDao.insertHistory((SearchHistoryBean) object);
            }
        });
    }

    @Override
    public void update(@NonNull Object object) {
        mExecutorService.execute(() -> {
            if (object instanceof TopicBean) {
                mTopicDao.updateTopic((TopicBean) object);
            } else if (object instanceof NewsBean) {
                mNewsDao.updateNews((NewsBean) object);
            } else if (object instanceof SearchHistoryBean) {
                mSearchHistoryDao.updateHistory((SearchHistoryBean) object);
            }
        });
    }
}
