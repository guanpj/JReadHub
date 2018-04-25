package com.jeez.guanpj.jreadhub.mvpframe.view.activity;

import android.os.Bundle;

import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

import javax.inject.Inject;

/**
 * Created by Jie on 2016-11-2.
 */

public abstract class AbsBaseMvpWebViewActivity<P extends BasePresenter> extends AbsBaseWebViewActivity implements IBaseView {
    @Inject
    public P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != mPresenter) {
            mPresenter.onAttatch(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroy();
    }
}
