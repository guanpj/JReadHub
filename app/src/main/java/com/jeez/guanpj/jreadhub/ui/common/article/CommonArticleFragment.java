package com.jeez.guanpj.jreadhub.ui.common.article;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.TopicNewsBean;
import com.jeez.guanpj.jreadhub.util.Constants;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public class CommonArticleFragment extends SwipeBackFragment implements Toolbar.OnMenuItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private TopicNewsBean mTopic;
    private String mUrl;
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    public static CommonArticleFragment newInstance(TopicNewsBean topic) {
        CommonArticleFragment fragment = new CommonArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_TOPIC, Parcels.wrap(topic));
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CommonArticleFragment newInstance(String url) {
        CommonArticleFragment fragment = new CommonArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_TOPIC_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this, view);
        mTopic = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_TOPIC));
        mUrl = getArguments().getString(Constants.EXTRA_TOPIC_URL);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initWebView();
    }

    private void initView() {
        TypedValue navIcon = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.navBackIcon, navIcon, true);

        mToolbar.setNavigationIcon(navIcon.resourceId);
        mToolbar.setNavigationOnClickListener(v -> pop());
        mToolbar.inflateMenu(R.menu.menu_topic);
        mToolbar.setOnMenuItemClickListener(this);

        mRefreshLayout.setColorSchemeColors(Color.parseColor("#607D8B"), Color.BLACK, Color.BLUE);
        mRefreshLayout.setOnRefreshListener(() -> {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            mWebView.reload();
        });
    }

    private void initWebView() {
        initWebSettings();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URLUtil.isNetworkUrl(url)) {
                    return super.shouldOverrideUrlLoading(view, url);
                }
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override public void onProgressChanged(WebView view, int newProgress) {
                //更新进度
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                    mRefreshLayout.setRefreshing(false);
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mToolbar.setTitle(title);
            }
        });
        mWebView.loadUrl(TextUtils.isEmpty(mUrl) ? mTopic.getUrl() : mUrl);
        mRefreshLayout.setRefreshing(true);
    }

    private void initWebSettings() {
        WebSettings mWebSetting = mWebView.getSettings();
        if (mWebSetting == null) return;
        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setUseWideViewPort(true);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setDomStorageEnabled(true);
        mWebSetting.setDatabaseEnabled(true);
        mWebSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        mWebSetting.setDatabasePath(cacheDirPath);
        mWebSetting.setAppCachePath(cacheDirPath);
        mWebSetting.setAppCacheEnabled(true);
        mWebSetting.setSupportZoom(true);
        mWebSetting.setBuiltInZoomControls(true);
        mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSetting.supportMultipleWindows();
        mWebSetting.setNeedInitialFocus(true);
        mWebSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSetting.setLoadsImagesAutomatically(true);
        mWebSetting.setDefaultTextEncodingName("utf-8");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open:
                Intent openIntent = new Intent(Intent.ACTION_VIEW);
                openIntent.setData(Uri.parse(mUrl));
                startActivity(Intent.createChooser(openIntent, "在浏览器中打开"));
                return true;
            case R.id.action_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, mUrl + "\n——来自 Readhub 客户端");
                startActivity(Intent.createChooser(shareIntent, "分享"));
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
