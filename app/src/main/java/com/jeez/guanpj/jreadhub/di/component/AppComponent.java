package com.jeez.guanpj.jreadhub.di.component;

import com.jeez.guanpj.jreadhub.app.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.di.module.AbstractAllActivityModule;
import com.jeez.guanpj.jreadhub.di.module.AbstractAllDialogFragmentModule;
import com.jeez.guanpj.jreadhub.di.module.AbstractAllFragmentModule;
import com.jeez.guanpj.jreadhub.di.module.AbstractAllSwipeBackActivityModule;
import com.jeez.guanpj.jreadhub.di.module.AbstractAllSwipeBackFragmentModule;
import com.jeez.guanpj.jreadhub.di.module.AppModule;
import com.jeez.guanpj.jreadhub.di.module.PopupWindowModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AbstractAllActivityModule.class,
        AbstractAllSwipeBackActivityModule.class,
        AbstractAllFragmentModule.class,
        AbstractAllSwipeBackFragmentModule.class,
        AbstractAllDialogFragmentModule.class,
        AppModule.class,
        PopupWindowModule.class})
public interface AppComponent {
    void inject(ReadhubApplicationLike app);
}
