package com.jeez.guanpj.mvpframework.support.delegate;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;

public class MVPDelegateCallbackProxy<V extends MVPView, P extends MVPPresenter<V>>
        implements MVPDelegateCallback<V, P> {
    private MVPDelegateCallback<V, P> mvpDelegateCallback;

    public MVPDelegateCallbackProxy(MVPDelegateCallback<V, P> mvpDelegateCallback) {
        this.mvpDelegateCallback = mvpDelegateCallback;
    }

    public void attachView() {
        getPresenter().attachView(getMvpView());
    }

    public void detachView() {
        getPresenter().detachView();
    }

    @Override
    public P createPresenter() {
        P presenter =  this.mvpDelegateCallback.getPresenter();
        if (presenter == null) {
            presenter = this.mvpDelegateCallback.createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("Presenter is null!");
        }
        this.mvpDelegateCallback.setPresenter(presenter);
        return getPresenter();
    }

    @Override
    public P getPresenter() {
        P presenter =  this.mvpDelegateCallback.getPresenter();
        /*if (presenter == null) {
            presenter = this.mvpDelegateCallback.createPresenter();
        }*/
        if (presenter == null) {
            throw new NullPointerException("Presenter is null!");
        }
        return null;
    }

    @Override
    public void setPresenter(P presenter) {
        this.mvpDelegateCallback.setPresenter(presenter);
    }

    @Override
    public V getMvpView() {
        return this.mvpDelegateCallback.getMvpView();
    }

    @Override
    public void setRetainInstance(boolean retaionInstance) {

    }

    @Override
    public boolean isRetainInstance() {
        return false;
    }

    @Override
    public boolean shouldInstanceBeRetained() {
        return false;
    }
}
