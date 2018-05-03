package com.jeez.guanpj.jreadhub.di.component;

import android.app.Activity;

import com.jeez.guanpj.jreadhub.di.module.FragmentModule;
import com.jeez.guanpj.jreadhub.di.scope.FragmentScope;
import com.jeez.guanpj.jreadhub.ui.blockchain.BlockChainFragment;
import com.jeez.guanpj.jreadhub.ui.developer.DeveloperFragment;
import com.jeez.guanpj.jreadhub.ui.topic.TopicFragment;
import com.jeez.guanpj.jreadhub.ui.topic.instant.InstantReadFragment;
import com.jeez.guanpj.jreadhub.ui.tech.TechFragment;
import com.jeez.guanpj.jreadhub.ui.topic.detail.TopicDetailFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(TopicFragment fragment);

    void inject(TechFragment fragment);

    void inject(BlockChainFragment fragment);

    void inject(DeveloperFragment fragment);

    void inject(InstantReadFragment fragment);

    void inject(TopicDetailFragment fragment);
}
