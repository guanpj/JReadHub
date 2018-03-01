package com.jeez.guanpj.mvpframework.support.delegate.activity;

import com.jeez.guanpj.mvpframework.base.presenter.MVPPresenter;
import com.jeez.guanpj.mvpframework.base.view.MVPView;

public class ActivityMvpConfigurationInstance<V extends MVPView, P extends MVPPresenter<V>> {
	private P presenter;
	private Object customeConfigurationInstance;

	public ActivityMvpConfigurationInstance(P presenter,
                                            Object customeConfigurationInstance) {
		super();
		this.presenter = presenter;
		this.customeConfigurationInstance = customeConfigurationInstance;
	}

	public P getPresenter() {
		return presenter;
	}

	public void setPresenter(P presenter) {
		this.presenter = presenter;
	}

	public Object getCustomeConfigurationInstance() {
		return customeConfigurationInstance;
	}

	public void setCustomeConfigurationInstance(
			Object customeConfigurationInstance) {
		this.customeConfigurationInstance = customeConfigurationInstance;
	}

}
