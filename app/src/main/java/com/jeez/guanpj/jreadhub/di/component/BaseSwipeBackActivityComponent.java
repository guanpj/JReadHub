package com.jeez.guanpj.jreadhub.di.component;

import com.jeez.guanpj.jreadhub.mvpframe.view.activity.AbsBaseMvpActivity;
import com.jeez.guanpj.jreadhub.mvpframe.view.activity.AbsBaseMvpSwipeBackActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseSwipeBackActivityComponent extends AndroidInjector<AbsBaseMvpSwipeBackActivity> {
    @Subcomponent.Builder
    abstract class BaseBuilder extends Builder<AbsBaseMvpSwipeBackActivity>{

    }
}
