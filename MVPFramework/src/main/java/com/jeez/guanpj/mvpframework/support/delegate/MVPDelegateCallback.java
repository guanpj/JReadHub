package com.jeez.guanpj.mvpframework.support.delegate;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;

public interface MVPDelegateCallback<V extends MVPView, P extends MVPPresenter<V>> {

    public P createPresenter();

    public P getPresenter();

    public void setPresenter(P presenter);

    public V getMvpView();

    public void setRetainInstance(boolean retaionInstance);

    public boolean isRetainInstance();

    public boolean shouldInstanceBeRetained();
}
