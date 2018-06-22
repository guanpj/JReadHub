package com.jeez.guanpj.jreadhub.di.module;

import android.app.Activity;

import com.jeez.guanpj.jreadhub.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return mActivity;
    }
}
