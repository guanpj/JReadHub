package com.jeez.guanpj.jreadhub;

import android.app.Application;

import com.jeez.guanpj.jreadhub.core.AppStatusTracker;
import com.jeez.guanpj.jreadhub.di.component.AppComponent;
import com.jeez.guanpj.jreadhub.di.component.DaggerAppComponent;
import com.jeez.guanpj.jreadhub.di.module.AppModule;

public class ReadhubApplication extends Application {
    private static ReadhubApplication sInstance;
    private static volatile AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppStatusTracker.init(this);
    }

    public static synchronized ReadhubApplication getInstance() {
        return sInstance;
    }

    public static synchronized AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(sInstance))
                    .build();
        }
        return appComponent;
    }
}
