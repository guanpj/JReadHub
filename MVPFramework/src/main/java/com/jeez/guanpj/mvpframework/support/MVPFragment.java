package com.jeez.guanpj.mvpframework.support;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;
import com.jeez.guanpj.mvpframework.support.delegate.fragment.FragmentMVPDelegate;
import com.jeez.guanpj.mvpframework.support.delegate.fragment.FragmentMVPDelegateCallback;
import com.jeez.guanpj.mvpframework.support.delegate.fragment.FragmentMVPDelegateImpl;

public abstract class MVPFragment<V extends MVPView, P extends MVPPresenter<V>>
        extends Fragment implements FragmentMVPDelegateCallback<V, P>, MVPView {

    private P presenter;
    private FragmentMVPDelegateImpl<V, P> fragmentMVPDelegate;

    protected FragmentMVPDelegate<V, P> getFragmentMVPDelegate() {
        if (this.fragmentMVPDelegate == null) {
            this.fragmentMVPDelegate = new FragmentMVPDelegateImpl<>(this);
        }
        return this.fragmentMVPDelegate;
    }

    @Override
    public P getPresenter() {
        return this.presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    public boolean isRetainInstance() {
        return false;
    }

    @Override
    public boolean shouldInstanceBeRetained() {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getFragmentMVPDelegate().onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentMVPDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getFragmentMVPDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFragmentMVPDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getFragmentMVPDelegate().onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentMVPDelegate().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentMVPDelegate().onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        getFragmentMVPDelegate().onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentMVPDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getFragmentMVPDelegate().onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getFragmentMVPDelegate().onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentMVPDelegate().onSaveInstanceState(outState);
    }
}
