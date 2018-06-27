package com.jeez.guanpj.jreadhub.di.component;

import android.widget.PopupWindow;

import com.jeez.guanpj.jreadhub.di.module.PopupWindowModule;
import com.jeez.guanpj.jreadhub.di.scope.PopupWindowScope;
import com.jeez.guanpj.jreadhub.module.topic.detail.relate.RelevantTopicWindow;

import dagger.Component;

@PopupWindowScope
@Component(dependencies = AppComponent.class, modules = PopupWindowModule.class)
public interface PopupWindowComponent {
    /*PopupWindow getPopupWindow();*/

    void inject(RelevantTopicWindow relevantTopicWindow);
}
