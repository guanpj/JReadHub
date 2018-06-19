package com.jeez.guanpj.jreadhub.widget.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jeez.guanpj.jreadhub.R;

import butterknife.ButterKnife;

public class AboutItemView extends LinearLayout {

    private Context mContext;

    public AboutItemView(Context context) {
        this(context, null);
    }

    public AboutItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AboutItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_setting_item, this, true);
        ButterKnife.bind(view);

        initView(attrs);
    }

    private void initView(AttributeSet attrs) {

    }
}
