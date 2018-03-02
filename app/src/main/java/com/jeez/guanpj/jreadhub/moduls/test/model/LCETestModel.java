package com.jeez.guanpj.jreadhub.moduls.test.model;

import android.content.Context;
import android.util.Log;

import com.jeez.guanpj.jreadhub.moduls.base.model.BaseModel;
import com.jeez.guanpj.jreadhub.moduls.test.bean.PostModel;
import com.jeez.guanpj.jreadhub.moduls.test.util.HttpUtils;
import com.jeez.guanpj.jreadhub.moduls.test.view.TestAdapter;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LCETestModel extends BaseModel {

    public LCETestModel(Context context) {
        super(context);
    }

    public void getTestData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("page", 0);
                paramMap.put("type", TestAdapter.Type.Text.type);
                String s = null;
                try {
                    s = HttpUtils.get(getServerUrl() + "api_open.php?a=list&c=data", paramMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("adf", s);
            }
        }).start();
    }

    public void getTestList(int page, int type, final HttpUtils.OnLceHttpResultListener onLceHttpResultListener) {
        ITestService service = buildService(ITestService.class);
        service.getLCETestList(page, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostModel>() {
                    @Override
                    public void onCompleted() {
                        onLceHttpResultListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onLceHttpResultListener.onError(new Exception(e));
                    }

                    @Override
                    public void onNext(PostModel model) {
                        onLceHttpResultListener.onResult(model);
                    }
                });
    }
}
