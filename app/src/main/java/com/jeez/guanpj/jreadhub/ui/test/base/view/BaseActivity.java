package com.jeez.guanpj.jreadhub.ui.test.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.jeez.guanpj.jreadhub.core.AppStatusTracker;
import com.jeez.guanpj.jreadhub.MainActivity;
import com.jeez.guanpj.jreadhub.ui.test.base.view.impl.IBaseFlowImpl;
import com.jeez.guanpj.jreadhub.constant.AppStatus;

public abstract class BaseActivity extends AppCompatActivity implements IBaseFlowImpl {
    protected Toolbar toolbar;
    protected TextView toolbar_title;
    public static final int MODE_BACK = 0;
    public static final int MODE_DRAWER = 1;
    public static final int MODE_NONE = 2;
    public static final int MODE_HOME = 3;
    private long mExitTime;
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void protectApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppStatus.KEY_HOME_ACTION, AppStatus.ACTION_RESTART_APP);
        startActivity(intent);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        switch (AppStatusTracker.getInstance().getAppStatus()) {
            case AppStatus.STATUS_FORCE_KILLED:
                protectApp();
                break;
            case AppStatus.STATUS_KICK_OUT:
                kickOut();
                break;
            case AppStatus.STATUS_LOGOUT:
            case AppStatus.STATUS_OFFLINE:
            case AppStatus.STATUS_ONLINE:
                initView();
                initData();
                initEvent();
                break;
            default:
        }
    }

    protected void kickOut() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppStatus.KEY_HOME_ACTION, AppStatus.ACTION_KICK_OUT);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isExit) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showShortToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
