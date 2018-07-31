package com.jeez.guanpj.jreadhub.module.settting;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.mvpframe.view.activity.AbsBaseMvpSwipeBackActivity;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.widget.ThemeDialog;
import com.jeez.guanpj.jreadhub.widget.custom.SettingItemView;
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
        return R.layout.fragment_setting;
    }

    @Override
    public void initView() {
        mThemeDialog = new ThemeDialog(this);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
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
        restartActivity();
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
