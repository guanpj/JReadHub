package com.jeez.guanpj.jreadhub.di.component;

import com.jeez.guanpj.jreadhub.mvpframe.view.activity.AbsBaseMvpActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseActivityComponent extends AndroidInjector<AbsBaseMvpActivity> {
    @Subcomponent.Builder
    abstract class BaseBuilder extends AndroidInjector.Builder<AbsBaseMvpActivity>{

    }
}
