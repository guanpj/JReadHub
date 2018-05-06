package com.jeez.guanpj.jreadhub;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jeez.guanpj.jreadhub.base.AbsBaseFragment;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarSearchClickEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarShareClickEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarNavigationClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.ui.adpter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends AbsBaseFragment implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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
                RxBus.getInstance().post(new ToolbarShareClickEvent());
                break;
        }
        return true;
    }
}
