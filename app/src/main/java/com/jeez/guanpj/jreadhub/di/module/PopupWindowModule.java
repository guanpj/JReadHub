package com.jeez.guanpj.jreadhub.di.module;

import android.widget.PopupWindow;

import com.jeez.guanpj.jreadhub.di.scope.PopupWindowScope;

import dagger.Module;
import dagger.Provides;

@Module
public class PopupWindowModule {
    private PopupWindow mPopupWindow;

    public PopupWindowModule(PopupWindow window) {
        mPopupWindow = window;
    }

    @Provides
    @PopupWindowScope
    PopupWindow providePopupWindow() {
        return mPopupWindow;
    }
}
