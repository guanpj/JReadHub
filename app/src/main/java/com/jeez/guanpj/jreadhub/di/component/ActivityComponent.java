package com.jeez.guanpj.jreadhub.di.component;

import android.app.Activity;

import com.jeez.guanpj.jreadhub.di.module.ActivityModule;
import com.jeez.guanpj.jreadhub.di.scope.ActivityScope;
import com.jeez.guanpj.jreadhub.module.star.main.MainActivity;
import com.jeez.guanpj.jreadhub.module.settting.SettingActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity activity);

    void inject(SettingActivity activity);
}
