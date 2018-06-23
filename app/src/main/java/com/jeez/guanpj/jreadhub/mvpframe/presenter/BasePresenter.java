package com.jeez.guanpj.jreadhub.mvpframe.presenter;

import android.content.Context;

import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.util.PreferenceHelper;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;
import com.jeez.guanpj.jreadhub.util.Constants;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Jie on 2016-11-2.
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    public Context context;
    private WeakReference<V> weakView;
    private V proxyView;
    private CompositeDisposable compositeDisposable;
    private DataManager mDataManager;

    public BasePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttatch(V view) {
        this.weakView = new WeakReference<>(view);
        proxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(), new MyInvocationHandler(this.weakView.get()));
    }

    @Override
    public void onDetach() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        if (this.weakView != null) {
            this.weakView.clear();
            this.weakView = null;
        }
    }

    @Override
    public @Constants.Theme String getTheme() {
        return PreferenceHelper.getInstant().getTheme();
    }

    @Override
    public void setTheme(String theme) {
        PreferenceHelper.getInstant().setTheme(theme);
    }

    @Override
    public boolean isUseSystemBrowser() {
        return PreferenceHelper.getInstant().isUseSystemBrowser();
    }

    @Override
    public void setUseSystemBrowser(boolean b) {
        PreferenceHelper.getInstant().setUseSystemBrowser(b);
    }

    @Override
    public void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public V getView() {
        return proxyView;
    }

    public boolean isAttached() {
        return this.weakView != null && this.weakView.get() != null;
    }

    private class MyInvocationHandler<T> implements InvocationHandler {
        private T target;

        public MyInvocationHandler(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws InvocationTargetException, IllegalAccessException {
            if (isAttached()) {
                return method.invoke(target, objects);
            }
            return null;
        }
    }
}
