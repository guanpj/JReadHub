package com.jeez.guanpj.jreadhub.di.module;

import android.arch.persistence.room.Room;

import com.jeez.guanpj.jreadhub.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.data.local.ReadhubDatabase;
import com.jeez.guanpj.jreadhub.data.local.dao.NewsDao;
import com.jeez.guanpj.jreadhub.data.local.dao.SearchHistoryDao;
import com.jeez.guanpj.jreadhub.data.local.dao.TopicDao;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    ReadhubDatabase provideDatabase() {
        return Room.databaseBuilder(ReadhubApplicationLike.getInstance(),
                ReadhubDatabase.class, "Readhub.db").build();
    }

    @Provides
    @Singleton
    TopicDao provideTopicDao(ReadhubDatabase database) {
        return database.getTopicDao();
    }

    @Provides
    @Singleton
    NewsDao provideNewsDao(ReadhubDatabase database) {
        return database.getNewsDao();
    }

    @Provides
    @Singleton
    SearchHistoryDao provideSearchHistoryDao(ReadhubDatabase database) {
        return database.getSearchHistoryDao();
    }

    @Provides
    @Singleton
    Executor provideExecutor() {
        return new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024));
    }

    @Provides
    @Singleton
    ExecutorService ExecutorService() {
        return Executors.newSingleThreadExecutor();
    }
}
