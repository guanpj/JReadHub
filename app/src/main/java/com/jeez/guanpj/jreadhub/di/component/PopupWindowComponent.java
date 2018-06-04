package com.jeez.guanpj.jreadhub.di.component;

import com.jeez.guanpj.jreadhub.di.module.PopupWindowModule;
import com.jeez.guanpj.jreadhub.di.scope.PopupWindowScope;
import com.jeez.guanpj.jreadhub.ui.topic.detail.relate.RelevantTopicWindow;

import dagger.Component;

@PopupWindowScope
@Component(dependencies = AppComponent.class, modules = PopupWindowModule.class)
public interface PopupWindowComponent {
    /*PopupWindow getPopupWindow();*/

    void inject(RelevantTopicWindow relevantTopicWindow);
}
