package com.jeez.guanpj.jreadhub.module.star.topic.star;

import com.jeez.guanpj.jreadhub.BuildConfig;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.module.BasePresenterTest;
import com.jeez.guanpj.jreadhub.module.star.topic.StarTopicContract;
import com.jeez.guanpj.jreadhub.module.star.topic.StarTopicPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class StarTopicPresenterTest extends BasePresenterTest {

    @Mock
    private StarTopicContract.View mView;

    private StarTopicPresenter mPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mPresenter = new StarTopicPresenter(mDataManager);
        mPresenter.onAttatch(mView);
    }

    @Test
    public void doRefresh() {
        mPresenter.doRefresh(false);
        Mockito.verify(mView).showLoading(false);
        Mockito.verify(mView).bindData(ArgumentMatchers.<TopicBean>anyList());
        Mockito.verify(mView).showContent();
    }

    @Test
    public void doLoadMore() {
    }
}