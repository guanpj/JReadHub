package com.jeez.guanpj.jreadhub.di.component;

import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpDialogFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseDialogFragmentComponent extends AndroidInjector<AbsBaseMvpDialogFragment> {

    @Subcomponent.Builder
    abstract class BaseBuilder extends AndroidInjector.Builder<AbsBaseMvpDialogFragment>{

    }
}
