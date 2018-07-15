package com.jeez.guanpj.jreadhub.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jeez.guanpj.jreadhub.base.IBaseViewFlow;
import com.jeez.guanpj.jreadhub.base.activity.AbsBaseActivity;
import com.jeez.guanpj.jreadhub.event.SetDrawerStatusEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public abstract class AbsBaseSwipeBackFragment extends SwipeBackFragment implements IBaseViewFlow {

    private Unbinder unBinder;
    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutId(), container, false);
        unBinder = ButterKnife.bind(this, mContentView);
        RxBus.getInstance().post(new SetDrawerStatusEvent(DrawerLayout.LOCK_MODE_LOCKED_CLOSED));
        initView();
        return attachToSwipeBack(mContentView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
    public void onDestroy() {
        super.onDestroy();
        if (null != unBinder) {
            unBinder.unbind();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Observable.timer(50, TimeUnit.MILLISECONDS).subscribe(timeout -> RxBus.getInstance().post(new SetDrawerStatusEvent(DrawerLayout.LOCK_MODE_UNDEFINED)));
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
