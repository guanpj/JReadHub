package com.jeez.guanpj.jreadhub.di.component;

import android.content.Context;

import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Context getAppContext();

    DataManager getDataManager();
}
