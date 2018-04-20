package com.jeez.guanpj.jreadhub.di.component;

import android.app.Activity;

import com.jeez.guanpj.jreadhub.di.module.ActivityModule;
import com.jeez.guanpj.jreadhub.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
