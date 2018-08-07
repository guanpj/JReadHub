package com.jeez.guanpj.jreadhub.module.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.MenuItem;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.fragment.AbsBaseFragment;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.app.AppStatus;
import com.jeez.guanpj.jreadhub.app.AppStatusTracker;
import com.jeez.guanpj.jreadhub.event.OpenWebSiteEvent;
import com.jeez.guanpj.jreadhub.event.SetDrawerStatusEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarNavigationClickEvent;
import com.jeez.guanpj.jreadhub.module.about.AboutFragment;
import com.jeez.guanpj.jreadhub.module.settting.SettingFragment;
import com.jeez.guanpj.jreadhub.module.splash.SplashActivity;
import com.jeez.guanpj.jreadhub.module.star.StarFragment;
import com.jeez.guanpj.jreadhub.module.web.WebViewFragment;
import com.jeez.guanpj.jreadhub.mvpframe.view.activity.AbsBaseMvpActivity;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.NavigationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends AbsBaseMvpActivity<MainPresenter> implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.dl_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nv_main)
    NavigationView mNavigationView;

    private long TOUCH_TIME = 0;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void performInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
        mNavigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void initDataAndEvent() {
        mNavigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerLayout.postDelayed(() -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    MainFragment mainFragment = findFragment(MainFragment.class);
                    start(mainFragment, SupportFragment.SINGLETASK);
                    break;
                case R.id.nav_star:
                    StarFragment starFragment = findFragment(StarFragment.class);
                    if (starFragment == null) {
                        startWithPopTo(StarFragment.newInstance(), MainFragment.class, false);
                    } else {
                        start(starFragment, SupportFragment.SINGLETASK);
                    }
                    break;
                case R.id.nav_setting:
                    //SettingActivity.start(this);
                    findFragment(MainFragment.class).start(SettingFragment.newInstance());
                    break;
                case R.id.nav_about:
                    findFragment(MainFragment.class).start(AboutFragment.newInstance());
                    break;
                default:
                    break;
            }
        }, 300);
        return true;
    }

    @Override
    public void onBackPressedSupport() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            ISupportFragment topFragment = getTopFragment();
            if (topFragment instanceof AbsBaseFragment) {
                mNavigationView.setCheckedItem(R.id.nav_home);
            }

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                pop();
            } else {
                if (System.currentTimeMillis() - TOUCH_TIME < Constants.EXIT_WAIT_TIME) {
                    AppStatusTracker.getInstance().exitApp();
                } else {
                    TOUCH_TIME = System.currentTimeMillis();
                    showShortToast(R.string.exit_tips);
                }
            }
        }
    }

    @Override
    public void onToolbarNavigationClickEvent(ToolbarNavigationClickEvent event) {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onSetDrawerStatusEvent(SetDrawerStatusEvent event) {
        if (event.getStatus() == DrawerLayout.LOCK_MODE_UNDEFINED) {
            if (getTopFragment() instanceof MainFragment || getTopFragment() instanceof StarFragment) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
            }
        } else {
            mDrawerLayout.setDrawerLockMode(event.getStatus());
        }
    }

    @Override
    public void onOpenWebSiteEvent(OpenWebSiteEvent event) {
        NewsBean newsBean = event.getNewsBean();
        if (mPresenter.isUseSystemBrowser()) {
            if (null != newsBean) {
                String url = null;
                if (!TextUtils.isEmpty(newsBean.getMobileUrl())) {
                    url = newsBean.getMobileUrl();
                } else {
                    url = newsBean.getUrl();
                }
                if (!TextUtils.isEmpty(url)) {
                    NavigationUtil.openInBrowser(this, event.getUrl());
                }
            } else if (!TextUtils.isEmpty(event.getUrl())) {
                NavigationUtil.openInBrowser(this, event.getUrl());
            }
        } else {
            if (null != newsBean) {
                findFragment(MainFragment.class).start(WebViewFragment.newInstance(newsBean));
            } else if (!TextUtils.isEmpty(event.getUrl())
                    && !TextUtils.isEmpty(event.getTitle())) {
                findFragment(MainFragment.class).start(WebViewFragment.newInstance(event.getUrl(), event.getTitle()));
            }
        }
    }

    @Override
    public void changeTheme() {
        restartActivity();
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        //return new DefaultHorizontalAnimator();
        // 设置无动画
        // return new DefaultNoAnimator();
        // 设置自定义动画
        // return new FragmentAnimator(enter,exit,popEnter,popExit);

        // 默认竖向(和安卓5.0以上的动画相同)
        return super.onCreateFragmentAnimator();
    }
}
