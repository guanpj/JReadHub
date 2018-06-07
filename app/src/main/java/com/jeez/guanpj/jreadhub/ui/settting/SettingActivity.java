package com.jeez.guanpj.jreadhub.ui.settting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.mvpframe.view.activity.AbsBaseMvpActivity;

public class SettingActivity extends AbsBaseMvpActivity<SettingPresenter> implements SettingContract.View {

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initDataAndEvent() {

    }

    @Override
    protected void performInject() {

    }

    @Override
    public void onFabClick() {

    }
}
