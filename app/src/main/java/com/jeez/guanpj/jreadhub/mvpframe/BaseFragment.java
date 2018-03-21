package com.jeez.guanpj.jreadhub.mvpframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jeez.guanpj.jreadhub.mvpframe.baseframe.BaseFuncIml;

/**
 * Created by Jie on 2016-11-1.
 */

public class BaseFragment extends Fragment implements BaseFuncIml {
    private View mContentView;
    private ViewGroup mContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        initEvent();
        initData();

        this.mContainer = container;
        return mContentView;
    }

    public void setContentView(int viewId) {
        this.mContentView = getActivity().getLayoutInflater().inflate(viewId, mContainer, false);
    }

    public View getContentView() {
        return mContentView;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    protected void showShortToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void openActivity(Class<? extends BaseActivity> targetActivity) {
        openActivity(targetActivity, null);
    }

    protected void openActivity(Class<? extends BaseActivity> targetActivity, Bundle parameter) {
        Intent intent = new Intent(getActivity(), targetActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
    }
}
