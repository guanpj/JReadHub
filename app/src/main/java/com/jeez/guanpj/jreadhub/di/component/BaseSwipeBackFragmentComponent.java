package com.jeez.guanpj.jreadhub.di.component;

import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpDialogFragment;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpSwipeBackFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseSwipeBackFragmentComponent extends AndroidInjector<AbsBaseMvpSwipeBackFragment> {

    @Subcomponent.Builder
    abstract class BaseBuilder extends Builder<AbsBaseMvpSwipeBackFragment>{

    }
}
