package com.jeez.guanpj.jreadhub.di.component;

import android.app.Activity;

import com.jeez.guanpj.jreadhub.di.module.FragmentModule;
import com.jeez.guanpj.jreadhub.di.scope.FragmentScope;
import com.jeez.guanpj.jreadhub.ui.blockchain.BlockChainFragment;
import com.jeez.guanpj.jreadhub.ui.developer.DeveloperFragment;
import com.jeez.guanpj.jreadhub.ui.hottest.HottestFragment;
import com.jeez.guanpj.jreadhub.ui.instant.InstantReadFragment;
import com.jeez.guanpj.jreadhub.ui.tech.TechFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(HottestFragment hottestFragment);

    void inject(TechFragment techFragment);

    void inject(BlockChainFragment techFragment);

    void inject(DeveloperFragment techFragment);

    void inject(InstantReadFragment fragment);
}
