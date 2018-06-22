package com.jeez.guanpj.jreadhub.di.module;

import android.app.Activity;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.jeez.guanpj.jreadhub.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment mFragment;
    private DialogFragment mDialogFragment;

    public FragmentModule(Fragment frgment) {
        this.mFragment = frgment;
    }

    public FragmentModule(DialogFragment fragment) {
        this.mFragment = null;
        this.mDialogFragment = fragment;
    }

    @Provides
    @FragmentScope
    Activity proviceActivity() {
        if (mFragment == null) {
            return mDialogFragment.getActivity();
        } else {
            return mFragment.getActivity();
        }
    }
}
