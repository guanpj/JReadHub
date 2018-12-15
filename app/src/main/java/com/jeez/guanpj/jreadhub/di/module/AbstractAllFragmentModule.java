package com.jeez.guanpj.jreadhub.di.module;

import com.jeez.guanpj.jreadhub.di.component.BaseFragmentComponent;
import com.jeez.guanpj.jreadhub.module.common.CommonListFragment;
import com.jeez.guanpj.jreadhub.module.common.CommonListFragmentModule;
import com.jeez.guanpj.jreadhub.module.main.MainActivity;
import com.jeez.guanpj.jreadhub.module.main.MainActivityModule;
import com.jeez.guanpj.jreadhub.module.star.news.StarCommonListFragment;
import com.jeez.guanpj.jreadhub.module.star.news.StarCommonListFragmentModule;
import com.jeez.guanpj.jreadhub.module.star.topic.StarTopicFragment;
import com.jeez.guanpj.jreadhub.module.star.topic.StarTopicFragmentModule;
import com.jeez.guanpj.jreadhub.module.topic.TopicFragment;
import com.jeez.guanpj.jreadhub.module.topic.TopicFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AbstractAllFragmentModule {
    @ContributesAndroidInjector(modules = CommonListFragmentModule.class)
    abstract CommonListFragment contributesCommonListFragmentInjector();

    @ContributesAndroidInjector(modules = StarCommonListFragmentModule.class)
    abstract StarCommonListFragment contributesStarCommonListFragmentInjector();

    @ContributesAndroidInjector(modules = StarTopicFragmentModule.class)
    abstract StarTopicFragment contributesStarTopicListFragmentInjector();

    @ContributesAndroidInjector(modules = TopicFragmentModule.class)
    abstract TopicFragment contributesTopicFragmentInjector();
}
