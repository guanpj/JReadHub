package com.jeez.guanpj.jreadhub.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.jeez.guanpj.jreadhub.R;

public class ThemeDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private OnThemeChangeListener onThemeChangeListener;

    public ThemeDialog(Context context) {
        this(context, R.style.ThemeDialog);

    }

    public ThemeDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initContentView();
    }

    private void initContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_theme, null);

        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        view.setMinimumWidth((int)(displayRectangle.width() * 0.8f));
        window.setBackgroundDrawableResource(R.color.dialog_bg);

        setContentView(view);

        view.findViewById(R.id.theme_blue).setOnClickListener(this);
        view.findViewById(R.id.theme_gray).setOnClickListener(this);
        view.findViewById(R.id.theme_white).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onThemeChangeListener != null) {
            onThemeChangeListener.onChangeTheme(v);
            dismiss();
        }
    }

    public interface OnThemeChangeListener {
        void onChangeTheme(View view);
    }

    public void setOnThemeChangeListener(OnThemeChangeListener onThemeChangeListener) {
        this.onThemeChangeListener = onThemeChangeListener;
    }
}
