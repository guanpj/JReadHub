package com.jeez.guanpj.jreadhub.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.data.local.dao.NewsDao;
import com.jeez.guanpj.jreadhub.data.local.dao.TopicDao;

@Database(entities = {TopicBean.class, NewsBean.class}, version = 1, exportSchema = false)
public abstract class ReadhubDatabase extends RoomDatabase {
    public abstract TopicDao getTopicDao();

    public abstract NewsDao getNewsDao();
}
