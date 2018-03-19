package com.jeez.guanpj.jreadhub.moduls.common;

import android.os.Bundle;

import com.jeez.guanpj.jreadhub.moduls.hottest.HottestPresenter;
import com.jeez.guanpj.jreadhub.moduls.hottest.HottestView;
import com.jeez.guanpj.mvpframework.base.view.MVPView;
import com.jeez.guanpj.mvpframework.support.MVPFragment;

public class TopicListFragment extends MVPFragment<HottestView, HottestPresenter> implements MVPView {

    @Override
    public HottestPresenter createPresenter() {
        return new HottestPresenter(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
