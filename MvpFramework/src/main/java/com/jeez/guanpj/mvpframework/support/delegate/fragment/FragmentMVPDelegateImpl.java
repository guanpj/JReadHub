package com.jeez.guanpj.mvpframework.support.delegate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.jeez.guanpj.mvpframework.base.presenter.MvpPresenter;
import com.jeez.guanpj.mvpframework.base.view.MvpView;
import com.jeez.guanpj.mvpframework.support.delegate.MvpDelegateCallbackProxy;

public class FragmentMVPDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements FragmentMVPDelegate<V, P> {

    private MvpDelegateCallbackProxy<V, P> proxy;
    private FragmentMVPDelegateCallback<V, P> fragmentMVPDelegateCallback;

    public FragmentMVPDelegateImpl(FragmentMVPDelegateCallback<V, P> fragmentMVPDelegateCallback) {
        if (fragmentMVPDelegateCallback == null) {
            throw new NullPointerException("delegateCallback is null!");
        }
        this.fragmentMVPDelegateCallback = fragmentMVPDelegateCallback;
    }

    private MvpDelegateCallbackProxy<V, P> getDelegateProxy() {
        if (this.proxy == null) {
            this.proxy = new MvpDelegateCallbackProxy<>(this.fragmentMVPDelegateCallback);
        }
        return this.proxy;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getDelegateProxy().createPresenter();
        getDelegateProxy().attachView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyView() {
        getDelegateProxy().detachView();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onDetach() {

    }
}
