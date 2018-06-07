package com.jeez.guanpj.jreadhub.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.AbsBaseActivity;
import com.jeez.guanpj.jreadhub.constant.AppStatus;
import com.jeez.guanpj.jreadhub.event.ChangeThemeEvent;
import com.jeez.guanpj.jreadhub.event.SetDrawerStatusEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarNavigationClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.ui.settting.SettingActivity;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.PermissionsChecker;
import com.jeez.guanpj.jreadhub.util.UncaughtException;
import com.jeez.guanpj.jreadhub.widget.ThemeDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends AbsBaseActivity implements NavigationView.OnNavigationItemSelectedListener, ThemeDialog.OnThemeChangeListener {

    @BindView(R.id.dl_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nv_main)
    NavigationView mNavigationView;

    private ThemeDialog mThemeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        } else {
            initErrorLogDetactor();
        }*/
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mThemeDialog = new ThemeDialog(this);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void initDataAndEvent() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mThemeDialog.setOnThemeChangeListener(this);

        RxBus.getInstance().toFlowable(ToolbarNavigationClickEvent.class)
                .subscribe(navigationClickEvent -> mDrawerLayout.openDrawer(GravityCompat.START));

        RxBus.getInstance().toFlowable(SetDrawerStatusEvent.class).subscribe(lockDrawerEvent ->
                mDrawerLayout.setDrawerLockMode(lockDrawerEvent.getStatus()));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int action = intent.getIntExtra(AppStatus.KEY_HOME_ACTION, AppStatus.ACTION_BACK_TO_HOME);
        switch (action) {
            case AppStatus.ACTION_KICK_OUT:
                break;
            case AppStatus.ACTION_LOGOUT:
                break;
            case AppStatus.ACTION_RESTART_APP:
                protectApp();
                break;
            case AppStatus.ACTION_BACK_TO_HOME:
                break;
            default:
        }
    }

    @Override
    protected void protectApp() {
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }

    private void requestPermission() {
        if (PermissionsChecker.checkPermissions(this, PermissionsChecker.storagePermissions)) {
            initErrorLogDetactor();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsChecker.REQUEST_STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initErrorLogDetactor();
            } else {
                showShortToast("权限获取失败");
            }
        }
    }

    private void initErrorLogDetactor() {
        UncaughtException mUncaughtException = UncaughtException.getInstance();
        mUncaughtException.init(this, getString(R.string.app_name));
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerLayout.post(() -> {
            switch (item.getItemId()) {
                case R.id.nav_setting:
                    SettingActivity.start(MainActivity.this);
                    break;
                case R.id.nav_about:
                    break;
                case R.id.nav_change_theme:
                    mThemeDialog.show();
                    break;
                default:
                    break;
            }
        });
        return true;
    }

    @Override
    public void onChangeTheme(String selectedTheme) {
        switch (selectedTheme) {
            case Constants.Theme.Blue:
                setTheme(R.style.BlueTheme);
                mThemeUtil.setTheme(Constants.Theme.Blue);
                break;
            case Constants.Theme.Gray:
                setTheme(R.style.GrayTheme);
                mThemeUtil.setTheme(Constants.Theme.Gray);
                break;
            default:
        }
        //changeTheme();
        reStartActivity();
    }

    private void reStartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void changeTheme() {
        RxBus.getInstance().post(new ChangeThemeEvent());
        refreshUI();
        //mCommonFragment.refreshUI();
        mDrawerLayout.closeDrawers();
    }

    private void refreshUI() {
        //状态栏
        TypedValue statusColor = new TypedValue();
        //主题
        TypedValue themeColor = new TypedValue();
        //状态栏字体颜色
        TypedValue toolbarTextColor = new TypedValue();
        //toolbar 导航图标
        TypedValue navIcon = new TypedValue();
        //toolbar 搜索图标
        TypedValue searchIcon = new TypedValue();
        //toolbar 更多图标
        TypedValue overFlowIcon = new TypedValue();

        //获取切换后的主题，以及主题相对应对的属性值
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.readhubStatus, statusColor, true);
        theme.resolveAttribute(R.attr.readhubTheme, themeColor, true);
        theme.resolveAttribute(R.attr.readhubToolbarText, toolbarTextColor, true);
        theme.resolveAttribute(R.attr.navIcon, navIcon, true);
        theme.resolveAttribute(R.attr.menuSearch, searchIcon, true);
        theme.resolveAttribute(R.attr.overFlowIcon, overFlowIcon, true);

        changeStatusColor(statusColor.resourceId);
    }

    private void changeStatusColor(int colorValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, colorValue));
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
         return new DefaultHorizontalAnimator();
        // 设置无动画
        // return new DefaultNoAnimator();
        // 设置自定义动画
        // return new FragmentAnimator(enter,exit,popEnter,popExit);

        // 默认竖向(和安卓5.0以上的动画相同)
        //return super.onCreateFragmentAnimator();
    }
}
