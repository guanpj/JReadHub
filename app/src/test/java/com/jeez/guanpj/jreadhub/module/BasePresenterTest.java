package com.jeez.guanpj.jreadhub.module;

import android.app.Application;

import com.jeez.guanpj.jreadhub.ReadhubApplicationLike;
import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.module.rule.MyRule;
import com.jeez.guanpj.jreadhub.module.rule.RxJavaRule;
import com.jeez.guanpj.jreadhub.module.rule.RxJavaTestSchedulerRule;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowLog;

public class BasePresenterTest {

    @Rule
    public MyRule mMyRule = new MyRule();
    @Rule
    public RxJavaRule mRxJavaRuler = new RxJavaRule();
    @Rule
    public RxJavaTestSchedulerRule mRxJavaTestSchedulerRule = new RxJavaTestSchedulerRule();
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    protected Application mApplication;
    protected DataManager mDataManager;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        mApplication = RuntimeEnvironment.application;
        mDataManager = ReadhubApplicationLike.getAppComponent().getDataManager();
    }
}
