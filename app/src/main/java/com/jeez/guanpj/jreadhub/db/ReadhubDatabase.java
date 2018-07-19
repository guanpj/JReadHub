package com.jeez.guanpj.jreadhub.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.jeez.guanpj.jreadhub.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.db.dao.NewsDao;
import com.jeez.guanpj.jreadhub.db.dao.TopicDao;

@Database(entities = {TopicBean.class, NewsBean.class}, version = 1, exportSchema = false)
public abstract class ReadhubDatabase extends RoomDatabase {
    private static final String DB_NAME = "Readhub.db";

    public static ReadhubDatabase getInstance() {
        return InstanceHolder.sInstance;
    }

    private static class InstanceHolder {
        private static ReadhubDatabase sInstance = Room.databaseBuilder(
                ReadhubApplicationLike.getInstance(), ReadhubDatabase.class, DB_NAME).build();
    }

    public abstract TopicDao getTopicDao();

    public abstract NewsDao getNewsDao();
}
