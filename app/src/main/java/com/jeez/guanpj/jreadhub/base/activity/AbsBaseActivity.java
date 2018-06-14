package com.jeez.guanpj.jreadhub.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.ReadhubApplication;
import com.jeez.guanpj.jreadhub.base.IBaseViewFlow;
import com.jeez.guanpj.jreadhub.constant.AppStatus;
import com.jeez.guanpj.jreadhub.core.AppStatusTracker;
import com.jeez.guanpj.jreadhub.ui.main.MainActivity;
import com.jeez.guanpj.jreadhub.ui.splash.SplashActivity;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.SharePreferencesHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Jie on 2016-10-26.
 */

public abstract class AbsBaseActivity extends SupportActivity implements IBaseViewFlow {

    private Unbinder unBinder;
    public static final int MODE_BACK = 0;
    public static final int MODE_DRAWER = 1;
    public static final int MODE_NONE = 2;
    public static final int MODE_HOME = 3;
    private long mExitTime;
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(getLayoutId());
        unBinder = ButterKnife.bind(this);
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
                initDataAndEvent();
                break;
            default:
        }
    }

    protected void protectApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppStatus.KEY_HOME_ACTION, AppStatus.ACTION_RESTART_APP);
        startActivity(intent);
    }

    protected void kickOut() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra(AppStatus.KEY_HOME_ACTION, AppStatus.ACTION_KICK_OUT);
        startActivity(intent);
    }

    private void initTheme() {
        String theme = SharePreferencesHelper.getInstance(this).getString(Constants.Key.THEME_MODE);
        switch (theme) {
            case Constants.ThemeType.Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Constants.ThemeType.Gray:
                setTheme(R.style.GrayTheme);
                break;
            default:
                setTheme(R.style.BlueTheme);
                break;
        }
    }

    @Override
    public abstract int getLayoutId();

    @Override
    public abstract void initView();

    @Override
    public abstract void initDataAndEvent();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != unBinder) {
            unBinder.unbind();
        }
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isExit) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showShortToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                AppStatusTracker.getInstance().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void showShortToast(String msg) {
        Toast.makeText(ReadhubApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void setToolbar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    protected void openActivity(Class<? extends AbsBaseActivity> targetActivity) {
        openActivity(targetActivity, null);
    }

    protected void openActivity(Class<? extends AbsBaseActivity> targetActivity, Bundle parameter) {
        Intent intent = new Intent(this, targetActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
    }
}