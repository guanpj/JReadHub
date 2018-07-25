package com.jeez.guanpj.jreadhub.core.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.core.db.dao.NewsDao;
import com.jeez.guanpj.jreadhub.core.db.dao.TopicDao;

@Database(entities = {TopicBean.class, NewsBean.class}, version = 1, exportSchema = false)
public abstract class ReadhubDatabase extends RoomDatabase {
    public abstract TopicDao getTopicDao();

    public abstract NewsDao getNewsDao();
}
