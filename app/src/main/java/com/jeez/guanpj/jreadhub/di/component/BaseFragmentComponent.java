package com.jeez.guanpj.jreadhub.di.component;

import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseFragmentComponent extends AndroidInjector<AbsBaseMvpFragment> {
    @Subcomponent.Builder
    abstract class BaseBuilder extends AndroidInjector.Builder<AbsBaseMvpFragment>{

    }
}
