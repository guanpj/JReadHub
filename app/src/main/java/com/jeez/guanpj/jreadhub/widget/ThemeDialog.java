package com.jeez.guanpj.jreadhub.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThemeDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private View mView;
    private OnThemeChangeListener onThemeChangeListener;
    @BindView(R.id.ll_wrapper)
    LinearLayout mWrapper;
    @BindView(R.id.theme_blue)
    ImageView imgThemeBlue;
    @BindView(R.id.theme_gray)
    ImageView imgThemeGray;
    @BindView(R.id.theme_dark)
    ImageView imgThemeDark;

    private String mCurrentTheme;

    public ThemeDialog(@NonNull Context context, String currentTheme) {
        this(context, R.style.ThemeDialog, currentTheme);
    }

    public ThemeDialog(@NonNull Context context, int themeResId, String currentTheme) {
        super(context, themeResId);
        this.mContext = context;
        this.mCurrentTheme = currentTheme;
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_theme, null, false);
        setContentView(mView);
        ButterKnife.bind(this, mView);
        initContentView();
    }

    private void initContentView() {
        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        mView.setMinimumWidth((int)(displayRectangle.width() * 0.8f));

        if (mCurrentTheme.equals(Constants.ThemeType.Blue)) {
            imgThemeBlue.setImageDrawable(new CircleImageDrawable(
                    mContext.getResources().getColor(R.color.theme_blue_theme), 25, true));
        } else {
            imgThemeBlue.setImageDrawable(new CircleImageDrawable(
                    mContext.getResources().getColor(R.color.theme_blue_theme), 25));
        }

        if (mCurrentTheme.equals(Constants.ThemeType.Gray)) {
            imgThemeGray.setImageDrawable(new CircleImageDrawable(
                    mContext.getResources().getColor(R.color.theme_gray_theme), 25, true));
        } else {
            imgThemeGray.setImageDrawable(new CircleImageDrawable(
                    mContext.getResources().getColor(R.color.theme_gray_theme), 25));
        }

        if (mCurrentTheme.equals(Constants.ThemeType.Dark)) {
            imgThemeDark.setImageDrawable(new CircleImageDrawable(
                    mContext.getResources().getColor(R.color.theme_dark_theme), 25, true));
        } else {
            imgThemeDark.setImageDrawable(new CircleImageDrawable(
                    mContext.getResources().getColor(R.color.theme_dark_theme), 25));
        }
    }

    @OnClick({R.id.theme_blue, R.id.theme_gray, R.id.theme_dark})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.theme_blue:
                if (!mCurrentTheme.equals(Constants.ThemeType.Blue)) {
                    if (onThemeChangeListener != null) {
                        onThemeChangeListener.onChangeTheme(Constants.ThemeType.Blue);
                    }
                }
                break;
            case R.id.theme_gray:
                if (!mCurrentTheme.equals(Constants.ThemeType.Gray)) {
                    if (onThemeChangeListener != null) {
                        onThemeChangeListener.onChangeTheme(Constants.ThemeType.Gray);
                    }
                }
                break;
            case R.id.theme_dark:
                if (!mCurrentTheme.equals(Constants.ThemeType.Dark)) {
                    if (onThemeChangeListener != null) {
                        onThemeChangeListener.onChangeTheme(Constants.ThemeType.Dark);
                    }
                }
                break;
            default:
                dismiss();
                break;
        }

        dismiss();
    }

    public String getCurrentTheme() {
        return mCurrentTheme;
    }

    public void setCurrentTheme(String currentTheme) {
        this.mCurrentTheme = currentTheme;
    }



    public interface OnThemeChangeListener {
        void onChangeTheme(@Constants.Theme String selectedTheme);
    }

    public void setOnThemeChangeListener(OnThemeChangeListener onThemeChangeListener) {
        this.onThemeChangeListener = onThemeChangeListener;
    }
}
