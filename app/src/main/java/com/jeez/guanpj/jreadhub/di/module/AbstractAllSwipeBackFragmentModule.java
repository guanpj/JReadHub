package com.jeez.guanpj.jreadhub.di.module;

import com.jeez.guanpj.jreadhub.di.component.BaseSwipeBackFragmentComponent;
import com.jeez.guanpj.jreadhub.module.common.CommonListFragmentModule;
import com.jeez.guanpj.jreadhub.module.main.MainActivity;
import com.jeez.guanpj.jreadhub.module.settting.SettingFragment;
import com.jeez.guanpj.jreadhub.module.settting.SettingFragmentModule;
import com.jeez.guanpj.jreadhub.module.star.search.SearchFragment;
import com.jeez.guanpj.jreadhub.module.star.search.SearchFragmentModule;
import com.jeez.guanpj.jreadhub.module.topic.detail.TopicDetailFragment;
import com.jeez.guanpj.jreadhub.module.topic.detail.TopicDetailFragmentModule;
import com.jeez.guanpj.jreadhub.module.web.WebViewFragment;
import com.jeez.guanpj.jreadhub.module.web.WebViewFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = BaseSwipeBackFragmentComponent.class)
public abstract class AbstractAllSwipeBackFragmentModule {
    @ContributesAndroidInjector(modules = SettingFragmentModule.class)
    abstract SettingFragment contributesSettingFragmentInjector();

    @ContributesAndroidInjector(modules = SearchFragmentModule.class)
    abstract SearchFragment contributesSearchFragmentInjector();

    @ContributesAndroidInjector(modules = TopicDetailFragmentModule.class)
    abstract TopicDetailFragment contributesTopicDetailFragmentInjector();

    @ContributesAndroidInjector(modules = WebViewFragmentModule.class)
    abstract WebViewFragment contributesWebViewFragmentInjector();
}
