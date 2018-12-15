package com.jeez.guanpj.jreadhub.module.topic.instant;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.InstantReadBean;
import com.jeez.guanpj.jreadhub.module.main.MainFragment;
import com.jeez.guanpj.jreadhub.module.web.WebViewFragment;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpDialogFragment;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.NavigationUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class InstantReadFragment extends AbsBaseMvpDialogFragment<InstantReadPresenter> implements InstantReadContract.View {

    public static final String TAG = "InstantReadFragment";

    @BindView(R.id.ll_progress_bar_wrapper)
    LinearLayout mProgressBarWrapper;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.txt_topic_instant_title)
    TextView mTxtTopicTitle;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.txt_instant_source)
    TextView mTxtSource;
    @BindView(R.id.txt_go_source)
    TextView mTxtJump2Source;

    private String mTopicId;
    private String mTheme;
    private String mDarkThemeJS;

    public static InstantReadFragment newInstance(String topicId) {
        InstantReadFragment fragment = new InstantReadFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_TOPIC_ID, topicId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTheme = mPresenter.getTheme();
        switch (mTheme) {
            case Constants.ThemeType.Blue:
                setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle_Blue);
                break;
            case Constants.ThemeType.Gray:
                setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle_Gray);
                break;
            case Constants.ThemeType.Dark:
                setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle_Dark);
                mDarkThemeJS = getDarkThemeJS();
                break;
            default:
                setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle_Base);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_instant_read;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initDataAndEvent() {
        mTopicId = Objects.requireNonNull(getArguments()).getString(Constants.BUNDLE_TOPIC_ID);
        initWebSettings();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.getTopicInstantRead(mTopicId);
    }

    private void initWebSettings() {
        WebSettings mWebSetting = mWebView.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setUseWideViewPort(true);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        mWebSetting.setLoadsImagesAutomatically(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (mTheme.equals(Constants.ThemeType.Dark)) {
                    mWebView.loadUrl(mDarkThemeJS);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URLUtil.isNetworkUrl(url)) {
                    dismiss();
                    if (mPresenter.isUseSystemBrowser()) {
                        NavigationUtil.openInBrowser(getActivity(), url);
                    } else {
                        ((SupportActivity) getContext()).findFragment(MainFragment.class)
                                .start(WebViewFragment.newInstance(url, ""));
                    }
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mTheme.equals(Constants.ThemeType.Dark)) {
                    mWebView.loadUrl(mDarkThemeJS);
                }
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100 && mProgressBarWrapper != null) {
                    mProgressBarWrapper.setVisibility(View.GONE);
                    if (mTheme.equals(Constants.ThemeType.Dark)) {
                        mWebView.loadUrl(mDarkThemeJS);
                    }
                } else if (mProgressBar != null) {
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Nullable
    private String getDarkThemeJS() {
        InputStream inputStream = null;
        String js = null;
        try {
            inputStream = getResources().getAssets().open("readhub_dark.js");
            if(inputStream != null){
                byte buff[] = new byte[1024];
                ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
                do {
                    int numRead = 0;
                    numRead = inputStream.read(buff);
                    if (numRead <= 0) {
                        break;
                    }
                    fromFile.write(buff, 0, numRead);
                } while (true);

                js = fromFile.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return js;
    }

    @Override
    public void onRequestStart() {
        mProgressBarWrapper.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(20);
    }

    @Override
    public void onRequestEnd(InstantReadBean data) {
        mTxtTopicTitle.setText(data.getTitle());
        mTxtSource.setText(getString(R.string.source_format, data.getSiteName()));
        mTxtJump2Source.setOnClickListener(v -> {
            dismiss();
            ((SupportActivity) getContext()).findFragment(MainFragment.class)
                .start(WebViewFragment.newInstance(data.getUrl(), data.getTitle()));
        });

        String css = "";
        if (mTheme.equals(Constants.ThemeType.Dark)) {
            css = "<link rel=\"stylesheet\" href=\"file:///android_asset/readhub_dark.css\" type=\"text/css\">";
        }

        String htmlHead = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                //+ css
                //+ "<link rel=\"stylesheet\" href=\"https://unpkg.com/mobi.css/dist/mobi.min.css\">"
                //+ "<link rel=\"stylesheet\" href=\"https://unpkg.com/mobi-plugin-color/dist/mobi-plugin-color.min.css\">"
                + "<style>"
                + "img{max-width:100% !important; width:auto; height:auto;}"
                + "body {font-size: 110%;word-spacing:110%；}"
                + "</style>"
                + "</head>";
        String htmlContent = "<html>"
                + htmlHead
                + "<body style:'height:auto;max-width: 100%; width:auto;'>"
                + data.getContent()
                + "</body></html>";
        mWebView.loadData(htmlContent, "text/html; charset=UTF-8", null);
    }

    @Override
    public void onRequestError() {
        showShortToast("请求错误");
        dismiss();
    }

    @OnClick(R.id.imb_close)
    void onCloseClick() {
        dismiss();
    }
}
