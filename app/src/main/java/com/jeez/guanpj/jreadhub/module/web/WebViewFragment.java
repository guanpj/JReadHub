package com.jeez.guanpj.jreadhub.module.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.NewsBean;
import com.jeez.guanpj.jreadhub.event.SetDrawerStatusEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpSwipeBackFragment;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.NavigationUtil;
import com.jeez.guanpj.jreadhub.util.ResourceUtil;
import com.just.agentweb.AgentWeb;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;

public class WebViewFragment extends AbsBaseMvpSwipeBackFragment<WebViewPresenter> implements WebViewContract.View, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fl_web_container)
    FrameLayout mWebContainer;
    @BindView(R.id.txt_toolbar_header)
    TextView mToolbarHeader;
    AgentWeb mAgentWeb;
    private NewsBean mNewsBean;
    private boolean mIsStar;
    private String mUrl;
    private String mTitle;

    public static WebViewFragment newInstance(String url, String title) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_TOPIC_URL, url);
        bundle.putString(Constants.BUNDLE_TOPIC_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static WebViewFragment newInstance(NewsBean newsBean) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BUNDLE_NEWS_BEAN, newsBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RxBus.getInstance().post(new SetDrawerStatusEvent(DrawerLayout.LOCK_MODE_LOCKED_CLOSED));
        if (null != getArguments().getSerializable(Constants.BUNDLE_NEWS_BEAN)) {
            mNewsBean = (NewsBean) getArguments().getSerializable(Constants.BUNDLE_NEWS_BEAN);
            if (!TextUtils.isEmpty(mNewsBean.getMobileUrl())) {
                mUrl = mNewsBean.getMobileUrl();
            } else {
                mUrl = mNewsBean.getUrl();
            }
            mTitle = mNewsBean.getTitle();
        } else {
            mUrl = getArguments().getString(Constants.BUNDLE_TOPIC_URL);
            mTitle = getArguments().getString(Constants.BUNDLE_TOPIC_TITLE);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != mNewsBean) {
            mPresenter.checkStar(mNewsBean.getId(), false);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web_view;
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void initView() {
        mToolbar.setNavigationIcon(ResourceUtil.getResource(getActivity(), R.attr.navBackIcon));
        mToolbar.setNavigationOnClickListener(v -> pop());
        mToolbar.inflateMenu(R.menu.menu_web);
        mToolbar.setTitle("");
        mToolbar.setOnMenuItemClickListener(this);
        if (null != mNewsBean) {
            mToolbar.getMenu().findItem(R.id.action_collect).setVisible(true);
        } else {
            mToolbar.getMenu().findItem(R.id.action_collect).setVisible(false);
        }
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
    public void onCheckStarResult(boolean isNewsExist, boolean showTips) {
        if (isNewsExist) {
            mToolbar.getMenu().findItem(R.id.action_collect).setIcon(R.drawable.ic_tool_bar_star_fill);
            if (showTips) {
                if (mIsStar) {
                    showShortToast(getString(R.string.tips_unstar_faild));
                } else {
                    showShortToast(getString(R.string.tips_star_success));
                }
            }
            mIsStar = true;
        } else {
            mToolbar.getMenu().findItem(R.id.action_collect).setIcon(R.drawable.ic_tool_bar_star_border);
            if (showTips) {
                if (mIsStar) {
                    showShortToast(getString(R.string.tips_unstar_success));
                } else {
                    showShortToast(getString(R.string.tips_star_faild));
                }
            }
            mIsStar = false;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_collect:
                if (mIsStar) {
                    mPresenter.removeStar(mNewsBean);
                } else {
                    mPresenter.addStar(mNewsBean);
                }
                Observable.timer(50, TimeUnit.MILLISECONDS)
                        .subscribe(observable -> mPresenter.checkStar(mNewsBean.getId(), true));
                return true;
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
