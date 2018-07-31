package com.jeez.guanpj.jreadhub.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SettingItemView extends LinearLayout {

    private static final int ITEM_TYPE_NONE = 0,
            ITEM_TYPE_SWITCH = 1,
            ITEM_TYPE_CHECKBOX = 2;

    @BindView(R.id.img_icon)
    AppCompatImageView mIcon;
    @BindView(R.id.txt_title)
    TextView mTitle;
    @BindView(R.id.txt_description)
    TextView mDescripttion;
    @BindView(R.id.switcher)
    SwitchCompat mSwitch;
    @BindView(R.id.checkbox)
    AppCompatCheckBox mCheckbox;

    private Context mContext;
    private int mType;
    private OnCheckedChangedListener mCheckedChangeListener;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_setting_item, this, true);
        ButterKnife.bind(view);

        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        int resourceId = ta.getResourceId(R.styleable.SettingItemView_icon, -1);
        String txtTitle = ta.getString(R.styleable.SettingItemView_title);
        String txtDescription = ta.getString(R.styleable.SettingItemView_description);
        mType = ta.getInt(R.styleable.SettingItemView_type, 0);

        mIcon.setImageResource(resourceId);
        mTitle.setText(txtTitle);
        if (TextUtils.isEmpty(txtDescription)) {
            mDescripttion.setVisibility(View.GONE);
        } else {
            mDescripttion.setText(txtDescription);
        }
        if (mType == ITEM_TYPE_SWITCH) {
            mSwitch.setVisibility(VISIBLE);
        } else if (mType == ITEM_TYPE_CHECKBOX) {
            mCheckbox.setVisibility(VISIBLE);
        }
    }

    public void setChecked(boolean checked) {
        if (mType == ITEM_TYPE_CHECKBOX) {
            mCheckbox.setChecked(checked);
        } else if (mType == ITEM_TYPE_SWITCH) {
            mSwitch.setChecked(checked);
        }
    }

    public boolean isChecked() {
        if (mType == ITEM_TYPE_CHECKBOX) {
            return mCheckbox.isChecked();
        } else if (mType == ITEM_TYPE_SWITCH) {
            return mSwitch.isChecked();
        }
        return false;
    }

    @OnCheckedChanged({R.id.switcher, R.id.checkbox})
    void onChcekedChanged() {
        if (null != mCheckedChangeListener) {
            if (mType == ITEM_TYPE_CHECKBOX) {
                mCheckedChangeListener.onCheckedChanged(mCheckbox.isChecked());
            } else if (mType == ITEM_TYPE_SWITCH) {
                mCheckedChangeListener.onCheckedChanged(mSwitch.isChecked());
            }
        }
    }

    public interface OnCheckedChangedListener {
        void onCheckedChanged(boolean isChecked);
    }

    public void setOnCheckedChangedListener(OnCheckedChangedListener listener) {
        this.mCheckedChangeListener = listener;
    }
}
