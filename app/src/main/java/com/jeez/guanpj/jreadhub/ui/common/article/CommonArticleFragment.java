package com.jeez.guanpj.jreadhub.ui.common.article;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
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
import android.widget.Toolbar;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.TopicNewsBean;
import com.jeez.guanpj.jreadhub.util.Constants;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public class CommonArticleFragment extends SwipeBackFragment implements Toolbar.OnMenuItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private TopicNewsBean mTopic;
    private String mUrl;
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    public static CommonArticleFragment newInstance(TopicNewsBean topic) {
        CommonArticleFragment fragment = new CommonArticleFragment();
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.EXTRA_TOPIC, Parcels.wrap(topic));
            fragment.setArguments(bundle);
        } catch (Exception e){
            e.printStackTrace();
        }
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
        toolbar.setNavigationOnClickListener(v -> pop());
        toolbar.inflateMenu(R.menu.menu_topic);
        toolbar.setOnMenuItemClickListener(this);

        refreshLayout.setColorSchemeColors(Color.parseColor("#607D8B"), Color.BLACK, Color.BLUE);
        refreshLayout.setOnRefreshListener(() -> {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webView.reload();
        });
    }

    private void initWebView() {
        initWebSettings();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URLUtil.isNetworkUrl(url)) return super.shouldOverrideUrlLoading(view, url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override public void onProgressChanged(WebView view, int newProgress) {
                //更新进度
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //mTxtTitle.setText(title);
                toolbar.setTitle(title);
            }
        });
        webView.loadUrl(TextUtils.isEmpty(mUrl) ? mTopic.getUrl() : mUrl);
        refreshLayout.setRefreshing(true);
    }

    private void initWebSettings() {
        WebSettings mWebSetting = webView.getSettings();
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
        return false;
    }

    @Override
    public boolean onBackPressedSupport() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.stopLoading();
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
    }
}
