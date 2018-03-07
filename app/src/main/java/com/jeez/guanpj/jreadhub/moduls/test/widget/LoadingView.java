package com.jeez.guanpj.jreadhub.moduls.test.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jeez.guanpj.jreadhub.R;

public class LoadingView extends RelativeLayout {

	private AnimationDrawable animationDrawable;

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LoadingView(Context context) {
		super(context);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.loading_view,this,false);
		addView(imageView);
		imageView.setBackgroundResource(R.drawable.essence_loading_frame);
		animationDrawable = (AnimationDrawable) imageView.getBackground();
	}
	
	public void openAnimation(){
		setVisibility(VISIBLE);
		animationDrawable.start();
	}
	
	public void closeAnimation(){
		animationDrawable.stop();
		setVisibility(View.GONE);
	}
	
}
