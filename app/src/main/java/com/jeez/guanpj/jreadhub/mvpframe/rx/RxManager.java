package com.jeez.guanpj.jreadhub.mvpframe.rx;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxManager {
    private RxBus mRxBus = RxBus.getInstance();
    private Map<String, Observable<?>> mObservables = new HashMap<>();
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public void on(String eventName, Consumer<Object> consumer) {
        Observable<?> observable = RxBus.getInstance().register(eventName);
        mObservables.put(eventName, observable);
        mDisposable.add(observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            }));
    }

    public void add(Disposable disposable) {
        mDisposable.add(disposable);
    }

    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }

    public void clear() {
        mDisposable.clear();
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()) {
            mRxBus.unRegister(entry.getKey(), entry.getValue());
        }
    }
}
