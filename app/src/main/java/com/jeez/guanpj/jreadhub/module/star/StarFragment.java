package com.jeez.guanpj.jreadhub.module.star;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.fragment.AbsBaseFragment;
import com.jeez.guanpj.jreadhub.base.fragment.AbsBaseSwipeBackFragment;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarNavigationClickEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarSearchClickEvent;
import com.jeez.guanpj.jreadhub.module.adpter.FragmentAdapter;
import com.jeez.guanpj.jreadhub.module.common.CommonListFragment;
import com.jeez.guanpj.jreadhub.module.star.news.StarCommonListFragment;
import com.jeez.guanpj.jreadhub.module.star.topic.StarTopicFragment;
import com.jeez.guanpj.jreadhub.module.topic.TopicFragment;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StarFragment extends AbsBaseSwipeBackFragment implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    public static StarFragment newInstance() {
        Bundle args = new Bundle();
        StarFragment fragment = new StarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    public void initView() {
        mToolbar.inflateMenu(R.menu.menu_star);
    }

    @Override
    public void initDataAndEvent() {
        List<String> pageTitles = new ArrayList<>();
        pageTitles.add("热门话题");
        pageTitles.add("资讯");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(StarTopicFragment.newInstance());
        fragments.add(StarCommonListFragment.newInstance());

        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager(), pageTitles, fragments);
        mViewPager.setAdapter(adapter);

        for (int i = 0; i < pageTitles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(pageTitles.get(i)));
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mToolbar.setNavigationOnClickListener(view -> RxBus.getInstance().post(new ToolbarNavigationClickEvent()));
        mToolbar.setOnMenuItemClickListener(this);
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        RxBus.getInstance().post(new FabClickEvent(mViewPager.getCurrentItem()));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                RxBus.getInstance().post(new ToolbarSearchClickEvent());
                showShortToast("Coming soon...");
                break;
            default:
                break;
        }
        return true;
    }
}
