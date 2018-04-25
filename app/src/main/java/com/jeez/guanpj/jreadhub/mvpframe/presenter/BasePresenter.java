package com.jeez.guanpj.jreadhub.mvpframe.presenter;

import android.content.Context;

import com.jeez.guanpj.jreadhub.mvpframe.rx.RxManager;
import com.jeez.guanpj.jreadhub.mvpframe.view.IBaseView;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Jie on 2016-11-2.
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    public Context context;
    private WeakReference<V> weakView;
    private V proxyView;
    public RxManager mRxManager = new RxManager();

    @Override
    public void onAttatch(V view) {
        this.weakView = new WeakReference<>(view);
        proxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(), new MyInvocationHandler(this.weakView.get()));
    }

    @Override
    public void onDetach() {
        mRxManager.clear();
        if (this.weakView != null) {
            this.weakView.clear();
            this.weakView = null;
        }
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
