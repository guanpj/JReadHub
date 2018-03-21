package com.jeez.guanpj.jreadhub.mvpframe;

import android.os.Bundle;

import com.jeez.guanpj.jreadhub.mvpframe.baseframe.BaseModel;
import com.jeez.guanpj.jreadhub.mvpframe.baseframe.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.baseframe.BaseView;
import com.longrise.jie.jforum.util.TUtil;

/**
 * Created by Jie on 2016-11-2.
 */

public abstract class BaseFrameWebViewActivity<P extends BasePresenter, M extends BaseModel> extends BaseWebViewActivity implements BaseView {
    public P mPresenter;
    public M mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onInternetError() {

    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDestroy();
        super.onDestroy();
    }
}
