package com.jeez.guanpj.jreadhub.module.adpter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<String> mTitles;
    private List<Fragment> mFragments;

    public FragmentAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments != null && mFragments.get(position) != null) {
            return mFragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    private void initFragments() {

    }
}
