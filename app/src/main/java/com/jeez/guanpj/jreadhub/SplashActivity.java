package com.jeez.guanpj.jreadhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.jeez.guanpj.jreadhub.base.BaseActivity;
import com.jeez.guanpj.jreadhub.constant.AppStatus;
import com.jeez.guanpj.jreadhub.core.AppStatusTracker;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusTracker.getInstance().setAppStatus(AppStatus.STATUS_ONLINE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initDataAndEvent() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        go2Main();
                    }
                });
    }

    private void go2Main() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
