package com.jeez.guanpj.jreadhub.di.module;

import android.arch.persistence.room.Room;

import com.jeez.guanpj.jreadhub.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.core.db.DatabaseHelper;
import com.jeez.guanpj.jreadhub.core.db.DatabaseHelperImpl;
import com.jeez.guanpj.jreadhub.core.db.ReadhubDatabase;
import com.jeez.guanpj.jreadhub.core.db.dao.NewsDao;
import com.jeez.guanpj.jreadhub.core.db.dao.TopicDao;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    DatabaseHelper provideDatabaseHelper(TopicDao topicDao, NewsDao newsDao, Executor executor) {
        return new DatabaseHelperImpl(topicDao, newsDao, executor);
    }

    @Provides
    ReadhubDatabase provideDatabase() {
        return Room.databaseBuilder(ReadhubApplicationLike.getInstance(),
                ReadhubDatabase.class, "Readhub.db").build();
    }

    @Provides
    TopicDao provideTopicDao(ReadhubDatabase database) {
        return database.getTopicDao();
    }

    @Provides
    NewsDao provideNewsDao(ReadhubDatabase database) {
        return database.getNewsDao();
    }

    @Singleton
    @Provides
    Executor provideExecutor() {
        return new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024));
    }
}
