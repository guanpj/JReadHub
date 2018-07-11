package com.jeez.guanpj.jreadhub.module.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.fragment.AbsBaseSwipeBackFragment;
import com.jeez.guanpj.jreadhub.event.SetDrawerStatusEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.NavigationUtil;
import com.jeez.guanpj.jreadhub.util.ResourceUtil;
import com.just.agentweb.AgentWeb;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;

public class CommonWebViewFragment extends AbsBaseSwipeBackFragment implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fl_web_container)
    FrameLayout mWebContainer;
    @BindView(R.id.txt_toolbar_header)
    TextView mToolbarHeader;
    AgentWeb mAgentWeb;
    private String mUrl;
    private String mTitle;

    public static CommonWebViewFragment newInstance(String url, String title) {
        CommonWebViewFragment fragment = new CommonWebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_TOPIC_URL, url);
        bundle.putString(Constants.EXTRA_TOPIC_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RxBus.getInstance().post(new SetDrawerStatusEvent(DrawerLayout.LOCK_MODE_LOCKED_CLOSED));
        mUrl = getArguments().getString(Constants.EXTRA_TOPIC_URL);
        mTitle = getArguments().getString(Constants.EXTRA_TOPIC_TITLE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web_view;
    }

    @Override
    public void initView() {
        mToolbar.setNavigationIcon(ResourceUtil.getResource(getActivity(), R.attr.navBackIcon));
        mToolbar.setNavigationOnClickListener(v -> pop());
        mToolbar.inflateMenu(R.menu.menu_web);
        mToolbar.setTitle("");
        mToolbar.setOnMenuItemClickListener(this);
        mToolbarHeader.setText(mTitle);
    }

    @Override
    public void initDataAndEvent() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebContainer, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open:
                NavigationUtil.openByApp(getActivity(), mUrl);
                return true;
            case R.id.action_share:
                NavigationUtil.shareToApp(getActivity(), mUrl);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Observable.timer(50, TimeUnit.MILLISECONDS).subscribe(
                timeout -> RxBus.getInstance().post(new SetDrawerStatusEvent(DrawerLayout.LOCK_MODE_UNDEFINED)));
    }
}
