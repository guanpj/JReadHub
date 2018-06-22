package com.jeez.guanpj.jreadhub.di.component;

import android.app.Activity;

import com.jeez.guanpj.jreadhub.di.module.FragmentModule;
import com.jeez.guanpj.jreadhub.di.scope.FragmentScope;
import com.jeez.guanpj.jreadhub.ui.common.CommonListFragment;
import com.jeez.guanpj.jreadhub.ui.settting.SettingFragment;
import com.jeez.guanpj.jreadhub.ui.topic.TopicFragment;
import com.jeez.guanpj.jreadhub.ui.topic.detail.TopicDetailFragment;
import com.jeez.guanpj.jreadhub.ui.topic.instant.InstantReadFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(TopicFragment fragment);

    void inject(InstantReadFragment fragment);

    void inject(TopicDetailFragment fragment);

    void inject(CommonListFragment fragment);

    void inject(SettingFragment fragment);
}
