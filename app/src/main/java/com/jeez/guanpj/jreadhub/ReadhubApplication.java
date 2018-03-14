package com.jeez.guanpj.jreadhub;

import android.app.Application;

public class ReadhubApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppStatusTracker.init(this);
    }
}
