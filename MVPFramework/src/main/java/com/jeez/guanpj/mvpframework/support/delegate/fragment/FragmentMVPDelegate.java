package com.jeez.guanpj.mvpframework.support.delegate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;

public interface FragmentMVPDelegate<V extends MVPView, P extends MVPPresenter<V>> {
    public void onCreate(Bundle savedInstanceState);

    public void onActivityCreated(Bundle savedInstanceState);

    public void onViewCreated(View view, Bundle savedInstanceState);

    public void onStart();

    public void onPause();

    public void onResume();

    public void onStop();

    public void onDestroyView();

    public void onDestroy();

    public void onSaveInstanceState(Bundle outState);

    public void onAttach(Context context);

    public void onDetach();
}
