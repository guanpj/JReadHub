package com.jeez.guanpj.jreadhub.di.module;

import com.jeez.guanpj.jreadhub.di.component.BaseDialogFragmentComponent;
import com.jeez.guanpj.jreadhub.module.topic.instant.InstantReadFragment;
import com.jeez.guanpj.jreadhub.module.topic.instant.InstantReadFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = BaseDialogFragmentComponent.class)
public abstract class AbstractAllDialogFragmentModule {
    @ContributesAndroidInjector(modules = InstantReadFragmentModule.class)
    abstract InstantReadFragment contributesInstantReadFragmentInjector();
}
