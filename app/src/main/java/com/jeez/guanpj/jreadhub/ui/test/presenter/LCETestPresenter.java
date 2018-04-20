package com.jeez.guanpj.jreadhub.ui.test.presenter;

import android.content.Context;

import com.jeez.guanpj.jreadhub.ui.test.base.presenter.BasePresenter;
import com.jeez.guanpj.jreadhub.ui.test.bean.PostModel;
import com.jeez.guanpj.jreadhub.ui.test.model.LCETestModel;
import com.jeez.guanpj.jreadhub.ui.test.util.HttpUtils;
import com.jeez.guanpj.jreadhub.ui.test.view.LCETestView;

public class LCETestPresenter extends BasePresenter<LCETestView> {

    private LCETestModel lceTestModel;

    public LCETestPresenter(Context context) {
        super(context);
        this.lceTestModel = new LCETestModel(context);
    }

    public void getLCETestList(int type, boolean pullToRefresh) {
        //lceTestModel.getTestData();
        getView().showLoading(pullToRefresh);
        lceTestModel.getTestList(0, type, new HttpUtils.OnLceHttpResultListener() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                getView().showError();
            }

            @Override
            public void onResult(Object result) {
                if (result == null) {
                    getView().bindData(null);
                } else {
                    getView().bindData((PostModel) result);
                }
                getView().showContent();
            }
        });
    }

}
