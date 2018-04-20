package com.jeez.guanpj.jreadhub.di.module;

import android.content.Context;

import com.jeez.guanpj.jreadhub.ReadhubApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final ReadhubApplication mApplication;

    public AppModule(ReadhubApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return mApplication;
    }
}
