package com.jeez.guanpj.jreadhub;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;

import com.jeez.guanpj.jreadhub.base.AbsBaseFragment;
import com.jeez.guanpj.jreadhub.event.ChangeThemeEvent;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarNavigationClickEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarSearchClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.ui.adpter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends AbsBaseFragment implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    public void initView() {
        mToolbar.inflateMenu(R.menu.menu_main);
    }

    @Override
    public void initDataAndEvent() {
        List<String> mPageTitles = new ArrayList<>();
        mPageTitles.add("热门话题");
        mPageTitles.add("科技动态");
        mPageTitles.add("开发者资讯");
        mPageTitles.add("区块链快讯");
        FragmentAdapter adapter = new FragmentAdapter(getFragmentManager(), mPageTitles);
        mViewPager.setAdapter(adapter);

        for (int i = 0; i < mPageTitles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mPageTitles.get(i)));
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mToolbar.setNavigationOnClickListener(view -> {
            RxBus.getInstance().post(new ToolbarNavigationClickEvent());
        });
        mToolbar.setOnMenuItemClickListener(this);

        RxBus.getInstance().toFlowable(ChangeThemeEvent.class).subscribe(changeThemeEvent -> {

            TypedValue themeColor = new TypedValue();        //主题
            TypedValue statusColor = new TypedValue();       //状态栏
            TypedValue toolbarTextColor = new TypedValue();  //状态栏字体颜色
            TypedValue navIcon = new TypedValue();           //toolbar 导航图标
            TypedValue searchIcon = new TypedValue();        //toolbar 搜索图标
            TypedValue overFlowIcon = new TypedValue();          //toolbar 更多图标

            //获取切换后的主题，以及主题相对应对的属性值
            Resources.Theme theme = getActivity().getTheme();
            theme.resolveAttribute(R.attr.readhubTheme, themeColor, true);
            theme.resolveAttribute(R.attr.readhubStatus, statusColor, true);
            theme.resolveAttribute(R.attr.readhubToolbarText, toolbarTextColor, true);
            theme.resolveAttribute(R.attr.navIcon, navIcon, true);
            theme.resolveAttribute(R.attr.menuSearch, searchIcon, true);
            theme.resolveAttribute(R.attr.overFlowIcon, overFlowIcon, true);

            //切换到主题相对应的图标以及颜色值
            mToolbar.getMenu().findItem(R.id.action_search).setIcon(searchIcon.resourceId);
            mToolbar.setNavigationIcon(navIcon.resourceId);
            mToolbar.setOverflowIcon(ContextCompat.getDrawable(getActivity(), overFlowIcon.resourceId));
            mToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), themeColor.resourceId));
            mToolbar.setTitleTextColor(ContextCompat.getColor(getActivity(), toolbarTextColor.resourceId));

            mTabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), toolbarTextColor.resourceId), ContextCompat.getColor(getActivity(), toolbarTextColor.resourceId));
            mTabLayout.setBackgroundResource(themeColor.resourceId);
            mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), toolbarTextColor.resourceId));

            mAppBarLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), themeColor.resourceId));
        });
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        RxBus.getInstance().post(new FabClickEvent());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                RxBus.getInstance().post(new ToolbarSearchClickEvent());
                showShortToast("Coming soon...");
                break;
            case R.id.action_share:
                doShare();
                break;
        }
        return true;
    }

    private void doShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "https://readhub.me\n互联网聚合阅读平台");
        startActivity(Intent.createChooser(intent, "分享"));
    }
}
