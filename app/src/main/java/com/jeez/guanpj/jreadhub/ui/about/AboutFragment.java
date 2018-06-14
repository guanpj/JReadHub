package com.jeez.guanpj.jreadhub.ui.about;

import android.content.res.Resources;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.BuildConfig;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.fragment.AbsBaseSwipeBackFragment;
import com.jeez.guanpj.jreadhub.event.OpenWebSiteEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class AboutFragment extends AbsBaseSwipeBackFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_readhub_site)
    LinearLayout mSiteLayout;
    @BindView(R.id.ll_code)
    LinearLayout mCodeLayout;
    @BindView(R.id.txt_version)
    TextView mVersion;

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    public void initView() {
        TypedValue navIcon = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.navBackIcon, navIcon, true);

        mToolbar.setNavigationIcon(navIcon.resourceId);
        mToolbar.setNavigationOnClickListener(v -> pop());

        mVersion.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    public void initDataAndEvent() {

    }

    @OnClick(R.id.ll_readhub_site)
    void go2ReadhubSite(View view) {
        RxBus.getInstance().post(new OpenWebSiteEvent("https://readhub.me"));
    }

    @OnClick(R.id.ll_code)
    void go2CodeSite(View view) {
        RxBus.getInstance().post(new OpenWebSiteEvent("https://github.com/guanpj/JReadHub"));
    }

    @OnClick(R.id.ll_version)
    void check4Update() {
        Beta.checkUpgrade();
    }

    /**
     * 单独设置转场动画
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }
}