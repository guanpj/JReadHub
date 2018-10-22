package com.jeez.guanpj.jreadhub.module.main;

import com.jeez.guanpj.jreadhub.data.DataManager;
import com.jeez.guanpj.jreadhub.event.ChangeThemeEvent;
import com.jeez.guanpj.jreadhub.event.OpenWebSiteEvent;
import com.jeez.guanpj.jreadhub.event.SetDrawerStatusEvent;
import com.jeez.guanpj.jreadhub.event.ToolbarNavigationClickEvent;
import com.jeez.guanpj.jreadhub.mvpframe.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private DataManager mDatamanager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDatamanager = dataManager;
    }

    @Override
    public void onAttach(MainContract.View view) {
        super.onAttach(view);
        initEvent();
    }

    private void initEvent() {
        addSubscribe(RxBus.getInstance().toFlowable(ToolbarNavigationClickEvent.class)
                .subscribe(navigationClickEvent -> getView().onToolbarNavigationClickEvent(navigationClickEvent)));

        addSubscribe(RxBus.getInstance().toFlowable(SetDrawerStatusEvent.class)
                .subscribe(setDrawerStatusEvent ->  getView().onSetDrawerStatusEvent(setDrawerStatusEvent)));

        addSubscribe(RxBus.getInstance().toFlowable(OpenWebSiteEvent.class)
                .subscribe(openWebSiteEvent -> getView().onOpenWebSiteEvent(openWebSiteEvent)));

        addSubscribe(RxBus.getInstance().toFlowable(ChangeThemeEvent.class).subscribe(changeThemeEvent -> getView().changeTheme()));
    }
}
