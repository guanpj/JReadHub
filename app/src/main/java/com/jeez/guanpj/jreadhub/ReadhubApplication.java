package com.jeez.guanpj.jreadhub;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.jeez.guanpj.jreadhub.core.AppStatusTracker;
import com.jeez.guanpj.jreadhub.di.component.AppComponent;
import com.jeez.guanpj.jreadhub.di.component.DaggerAppComponent;
import com.jeez.guanpj.jreadhub.di.module.AppModule;
import com.jeez.guanpj.jreadhub.util.CrashHandler;

public class ReadhubApplication extends Application {
    private static ReadhubApplication sInstance;
    private static volatile AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppStatusTracker.init(this);
        AndroidThreeTen.init(this);
        CrashHandler.getInstance().init(getApplicationContext());
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
