package com.jeez.guanpj.jreadhub.di.module;

import android.app.Activity;
import android.app.Fragment;

import com.jeez.guanpj.jreadhub.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment frgment) {
        this.mFragment = frgment;
    }

    @Provides
    @FragmentScope
    Activity proviceActivity() {
        return mFragment.getActivity();
    }
}
