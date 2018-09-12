package com.jeez.guanpj.jreadhub.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.SearchHistoryBean;
import com.jeez.guanpj.jreadhub.bean.TopicDetailBean;
import com.jeez.guanpj.jreadhub.data.local.dao.NewsDao;
import com.jeez.guanpj.jreadhub.data.local.dao.SearchHistoryDao;
import com.jeez.guanpj.jreadhub.data.local.dao.TopicDetailDao;

@Database(entities = {TopicDetailBean.class, NewsBean.class, SearchHistoryBean.class}, version = 1, exportSchema = false)
public abstract class ReadhubDatabase extends RoomDatabase {
    public abstract TopicDetailDao getTopicDetailDao();

    public abstract NewsDao getNewsDao();

    public abstract SearchHistoryDao getSearchHistoryDao();
}
