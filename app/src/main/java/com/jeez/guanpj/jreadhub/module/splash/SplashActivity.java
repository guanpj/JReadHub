package com.jeez.guanpj.jreadhub.module.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.activity.AbsBaseActivity;
import com.jeez.guanpj.jreadhub.app.AppStatus;
import com.jeez.guanpj.jreadhub.app.AppStatusTracker;
import com.jeez.guanpj.jreadhub.module.main.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;

public class SplashActivity extends AbsBaseActivity {

    @BindView(R.id.txt_name)
    TextView mAppName;
    @BindView(R.id.txt_des)
    TextView mAppDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusTracker.getInstance().setAppStatus(AppStatus.STATUS_ONLINE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initDataAndEvent() {
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mAppDes, "alpha", 0, 1f);
        ObjectAnimator desAnim = ObjectAnimator.ofFloat(mAppDes, "translationX", -500f, 0f);
        desAnim.setDuration(700);
        desAnim.setInterpolator(new DecelerateInterpolator());
        desAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mAppName.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAppName.setVisibility(View.VISIBLE);
            }
        });
        ObjectAnimator nameAnim = ObjectAnimator.ofFloat(mAppName, "translationY", -500f, 0f);
        nameAnim.setDuration(2000);
        nameAnim.setInterpolator(new BounceInterpolator());
        nameAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(time -> go2Main());
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(desAnim).with(alphaAnim).before(nameAnim);
        animSet.start();
    }

    private void go2Main() {
        MainActivity.start(this);
        finish();
    }
}
