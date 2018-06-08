package com.jeez.guanpj.jreadhub.ui.about;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.AbsBaseActivity;

import butterknife.BindView;

public class AboutActivity extends AbsBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        TypedValue navIcon = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.navBackIcon, navIcon, true);

        mToolbar.setNavigationIcon(navIcon.resourceId);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void initDataAndEvent() {

    }
}
