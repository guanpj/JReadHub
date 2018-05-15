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

    public ThemeDialog(@NonNull Context context) {
        this(context, R.style.ThemeDialog);
    }

    public ThemeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_theme, null, false);

        /*try {
            mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_theme, null, false);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        setContentView(mView);
        ButterKnife.bind(this, mView);
        initContentView();
    }

    private void initContentView() {
        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        mView.setMinimumWidth((int)(displayRectangle.width() * 0.8f));
        window.setBackgroundDrawableResource(R.color.dialog_bg);

        imgThemeBlue.setImageDrawable(new CircleImageDrawable(mContext.getResources().getColor(R.color.theme_blue_theme), 25));
        imgThemeGray.setImageDrawable(new CircleImageDrawable(mContext.getResources().getColor(R.color.theme_gray_theme), 25));
    }

    @OnClick({R.id.theme_blue, R.id.theme_gray})
    public void onClick(View v) {
        String selectedTheme = null;
        switch (v.getId()) {
            case R.id.theme_blue:
                selectedTheme = Constants.Theme.Blue;
                break;
            case R.id.theme_gray:
                selectedTheme = Constants.Theme.Gray;
                break;
            default:
                break;
        }

        if (onThemeChangeListener != null) {
            onThemeChangeListener.onChangeTheme(selectedTheme);
            dismiss();
        }
    }

    public interface OnThemeChangeListener {
        void onChangeTheme(String selectedTheme);
    }

    public void setOnThemeChangeListener(OnThemeChangeListener onThemeChangeListener) {
        this.onThemeChangeListener = onThemeChangeListener;
    }
}
