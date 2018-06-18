package com.jeez.guanpj.jreadhub.ui.settting;

import android.content.res.Resources;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.event.ChangeThemeEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpSwipeBackFragment;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.widget.ThemeDialog;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class SettingFragment extends AbsBaseMvpSwipeBackFragment<SettingPresenter> implements SettingContract.View, ThemeDialog.OnThemeChangeListener {

    private ThemeDialog mThemeDialog;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.switch_browser)
    SwitchCompat mSwithCompat;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        TypedValue navIcon = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.navBackIcon, navIcon, true);

        mThemeDialog = new ThemeDialog(getActivity());
        mToolbar.setNavigationIcon(navIcon.resourceId);
        mToolbar.setNavigationOnClickListener(v -> pop());
    }

    @Override
    public void initDataAndEvent() {
        mSwithCompat.setChecked(mPresenter.isUseSystemBrowser());
        mThemeDialog.setOnThemeChangeListener(this);
    }

    @OnCheckedChanged(R.id.switch_browser)
    void swithBrowser() {
        mPresenter.setUseSystemBrowser(mSwithCompat.isChecked());
    }

    @OnClick(R.id.ll_change_theme)
    void change() {
        mThemeDialog.show();
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onChangeTheme(@Constants.Theme String selectedTheme) {
        mPresenter.setTheme(selectedTheme);
        RxBus.getInstance().post(new ChangeThemeEvent());
    }

    /**
     * 单独设置转场动画
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }

    /*private void refreshUI() {
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
    }*/
}
