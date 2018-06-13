package com.jeez.guanpj.jreadhub.ui.topic.instant;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.ReadhubApplication;
import com.jeez.guanpj.jreadhub.bean.InstantReadBean;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpDialogFragment;
import com.jeez.guanpj.jreadhub.ui.common.CommonWebViewFragment;
import com.jeez.guanpj.jreadhub.ui.main.MainFragment;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.NavigationUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class InstantReadFragment extends AbsBaseMvpDialogFragment<InstantReadPresenter> implements InstantReadContract.View {

    public static final String TAG = "InstantReadFragment";
    @BindView(R.id.ll_content_wrapper)
    LinearLayout mContentWrapper;
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

    private String mTopicId;

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
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle);
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_instant_read;
    }

    @Override
    public void initView() {
        Rect displayRectangle = new Rect();
        Objects.requireNonNull(getDialog().getWindow()).getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                (int)(displayRectangle.height() * 0.8f));
        mContentWrapper.setLayoutParams(params);
    }

    @Override
    public void initDataAndEvent() {
        mTopicId = Objects.requireNonNull(getArguments()).getString(Constants.BUNDLE_TOPIC_ID);
        initWebSettings();
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
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (URLUtil.isNetworkUrl(request.getUrl().toString())) {
                    dismiss();
                    if (mPresenter.isUseSystemBrowser()) {
                        NavigationUtil.openInBrowser(getActivity(), request.getUrl().toString());
                    } else {
                        ((SupportActivity) getContext()).findFragment(MainFragment.class)
                                .start(CommonWebViewFragment.newInstance(request.getUrl().toString()));
                    }
                }
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (request.getUrl().toString().endsWith("mobi.min.css")) {
                    //使用本地 css 优化阅读视图
                    WebResourceResponse resourceResponse = null;
                    try {
                        InputStream in = ReadhubApplication.getInstance().getAssets().open("css/mobi.css");
                        resourceResponse = new WebResourceResponse("text/css", "UTF-8", in);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (resourceResponse != null) {
                        return resourceResponse;
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBarWrapper.setVisibility(View.GONE);
                } else {
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    public void onRequestStart() {
        mProgressBarWrapper.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(20);
    }

    @Override
    public void onRequestEnd(InstantReadBean data) {
        if (data == null) {
            return;
        }
        mTxtTopicTitle.setText(data.getTitle());
        mTxtSource.setText(getString(R.string.source_format, data.getSiteName()));
        String htmlHead = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<link rel=\"stylesheet\" href=\"https://unpkg.com/mobi.css/dist/mobi.min.css\">"
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

    @Override
    public void onFabClick() {

    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        dismiss();
    }
}
