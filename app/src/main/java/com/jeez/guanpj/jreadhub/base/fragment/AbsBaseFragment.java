package com.jeez.guanpj.jreadhub.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jeez.guanpj.jreadhub.base.IBaseViewFlow;
import com.jeez.guanpj.jreadhub.base.activity.AbsBaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Jie on 2016-11-1.
 */

public abstract class AbsBaseFragment extends SupportFragment implements IBaseViewFlow {

    private Unbinder unBinder;
    private View mContentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), container, false);
        }
        unBinder = ButterKnife.bind(this, mContentView);
        initView();
        //判断Fragment对应的Activity是否存在这个视图
        ViewGroup parent = (ViewGroup) mContentView.getParent();
        if (parent != null){
            parent.removeView(mContentView);
        }
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataAndEvent();
    }

    @Override
    public abstract int getLayoutId();

    @Override
    public abstract void initView();

    @Override
    public abstract void initDataAndEvent();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
