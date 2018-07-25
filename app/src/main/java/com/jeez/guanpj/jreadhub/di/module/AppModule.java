package com.jeez.guanpj.jreadhub.di.module;

import android.content.Context;

import com.jeez.guanpj.jreadhub.data.DataManager;
import com.jeez.guanpj.jreadhub.data.local.LocalDataSource;
import com.jeez.guanpj.jreadhub.data.local.LocalRepository;
import com.jeez.guanpj.jreadhub.data.remote.RemoteDataSource;
import com.jeez.guanpj.jreadhub.data.remote.RemoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkModule.class, DatabaseModule.class})
public class AppModule {
    private final Context mContext;

    public AppModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return mContext;
    }

    @Provides
    @Singleton
    RemoteDataSource provideRemoteDataSource(RemoteRepository remoteRepository) {
        return remoteRepository;
    }

    @Provides
    @Singleton
    LocalDataSource provideLocalDataSource(LocalRepository localRepository) {
        return localRepository;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        return new DataManager(remoteDataSource, localDataSource);
    }
}
