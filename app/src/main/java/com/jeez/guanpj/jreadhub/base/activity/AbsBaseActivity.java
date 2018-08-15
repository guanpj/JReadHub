package com.jeez.guanpj.jreadhub.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.app.AppStatus;
import com.jeez.guanpj.jreadhub.app.AppStatusTracker;
import com.jeez.guanpj.jreadhub.app.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.base.IBaseViewFlow;
import com.jeez.guanpj.jreadhub.module.main.MainActivity;
import com.jeez.guanpj.jreadhub.module.splash.SplashActivity;
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
            case Constants.ThemeType.Dark:
                setTheme(R.style.DarkTheme);
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

    protected void showShortToast(String msg) {
        Toast.makeText(ReadhubApplicationLike.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showShortToast(int resId) {
        Toast.makeText(ReadhubApplicationLike.getInstance(), resId, Toast.LENGTH_SHORT).show();
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
