package com.jeez.guanpj.jreadhub.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jeez.guanpj.jreadhub.base.IBaseViewFlow;
import com.jeez.guanpj.jreadhub.base.activity.AbsBaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbsBaseDialogFragment extends DialogFragment implements IBaseViewFlow {

    private Unbinder unBinder;
    private View mContentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unBinder = ButterKnife.bind(this, view);
        initView();
        initDataAndEvent();
        return view;
    }

    @Override
    public abstract int getLayoutId();

    @Override
    public abstract void initView();

    @Override
    public abstract void initDataAndEvent();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != unBinder) {
            unBinder.unbind();
        }
    }

    protected void showShortToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void openActivity(Class<? extends AbsBaseActivity> targetActivity) {
        openActivity(targetActivity, null);
    }

    protected void openActivity(Class<? extends AbsBaseActivity> targetActivity, Bundle parameter) {
        Intent intent = new Intent(getActivity(), targetActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
    }
}
