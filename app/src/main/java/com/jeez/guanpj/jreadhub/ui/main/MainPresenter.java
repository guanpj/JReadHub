package com.jeez.guanpj.jreadhub.ui.main;

import com.jeez.guanpj.jreadhub.core.DataManager;
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
    public void onAttatch(MainContract.View view) {
        super.onAttatch(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getInstance().toFlowable(ToolbarNavigationClickEvent.class)
                .subscribe(navigationClickEvent -> getView().onToolbarNavigationClickEvent(navigationClickEvent)));

        addSubscribe(RxBus.getInstance().toFlowable(SetDrawerStatusEvent.class)
                .subscribe(setDrawerStatusEvent ->  getView().onSetDrawerStatusEvent(setDrawerStatusEvent)));
    }
}
