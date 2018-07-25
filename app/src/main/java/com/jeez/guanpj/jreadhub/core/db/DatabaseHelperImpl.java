package com.jeez.guanpj.jreadhub.core.db;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.db.dao.NewsDao;
import com.jeez.guanpj.jreadhub.core.db.dao.TopicDao;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class DatabaseHelperImpl implements DatabaseHelper {

    private NewsDao mNewsDao;
    private TopicDao mTopicDao;
    private Executor mExecutor;

    @Inject
    public DatabaseHelperImpl(TopicDao topicDao, NewsDao newsDao, Executor executor) {
        mTopicDao = topicDao;
        mNewsDao = newsDao;
        mExecutor = executor;
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
            return (Single<T>) mTopicDao.getTopicById2(id);
        } else if (NewsBean.class.equals(tClass)) {
            return (Single<T>) mNewsDao.getNewsById2(id);
        }
        return null;
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
    public void delete(Object object) {
        mExecutor.execute(() -> {
            if (object instanceof TopicBean) {
                mTopicDao.deleteTopic((TopicBean) object);
            } else if (object instanceof NewsBean) {
                mNewsDao.deleteNews((NewsBean) object);
            }
        });
    }

    @Override
    public void insert(Object object) {
        mExecutor.execute(() -> {
            if (object instanceof TopicBean) {
                mTopicDao.insertTopic((TopicBean) object);
            } else if (object instanceof NewsBean) {
                mNewsDao.insertNews((NewsBean) object);
            }
        });
    }
}
