package com.jeez.guanpj.jreadhub.di.module;

import com.jeez.guanpj.jreadhub.di.component.BaseActivityComponent;
import com.jeez.guanpj.jreadhub.module.main.MainActivity;
import com.jeez.guanpj.jreadhub.module.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AbstractAllActivityModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributesMainActivityInjector();
}
