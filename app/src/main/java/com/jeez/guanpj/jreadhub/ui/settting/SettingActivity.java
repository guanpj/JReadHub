package com.jeez.guanpj.jreadhub.ui.settting;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.event.ChangeThemeEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.view.activity.AbsBaseMvpSwipeBackActivity;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.ResourceUtil;
import com.jeez.guanpj.jreadhub.widget.custom.SettingItemView;
import com.jeez.guanpj.jreadhub.widget.ThemeDialog;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends AbsBaseMvpSwipeBackActivity<SettingPresenter> implements SettingContract.View, ThemeDialog.OnThemeChangeListener {

    private ThemeDialog mThemeDialog;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.item_use_system_browser)
    SettingItemView mUserSystemBrowserItem;
    @BindView(R.id.item_auto_update)
    SettingItemView mAutoUpdateItem;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        mThemeDialog = new ThemeDialog(this);
        mToolbar.setNavigationIcon(ResourceUtil.getResource(this, R.attr.navBackIcon));
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void initDataAndEvent() {
        mUserSystemBrowserItem.setChecked(mPresenter.isUseSystemBrowser());
        mUserSystemBrowserItem.setOnCheckedChangedListener(isChecked ->
            mPresenter.setUseSystemBrowser(isChecked));

        mAutoUpdateItem.setChecked(mPresenter.isAutoUpgrade());
        mAutoUpdateItem.setOnCheckedChangedListener(isChecked ->
                Beta.autoCheckUpgrade = isChecked);

        mThemeDialog.setOnThemeChangeListener(this);
    }

    @OnClick(R.id.item_switch_theme)
    void change() {
        mThemeDialog.show();
    }

    @Override
    protected void performInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void onChangeTheme(@Constants.Theme String selectedTheme) {
        mPresenter.setTheme(selectedTheme);
        switch (selectedTheme) {
            case Constants.ThemeType.Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Constants.ThemeType.Gray:
                setTheme(R.style.GrayTheme);
                break;
            default:
                setTheme(R.style.BlueTheme);
        }
        //changeTheme();
        restartActivity();
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void changeTheme() {
        RxBus.getInstance().post(new ChangeThemeEvent());
        refreshUI();
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
}
