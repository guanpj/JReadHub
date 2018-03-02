package com.jeez.guanpj.mvpframework.base.presenter;

import android.content.Context;

import com.jeez.guanpj.mvpframework.base.view.MVPView;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class MVPBasePresenter<V extends MVPView> implements MVPPresenter<V> {

    private WeakReference<Context> weakContext;
    private WeakReference<V> weakView;
    private V proxyView;

    public MVPBasePresenter(Context context) {
        this.weakContext = new WeakReference<Context>(context);
    }

    public Context getContext() {
        return weakContext.get();
    }

    public V getView() {
        return proxyView;
    }

    public boolean isAttachedView() {
        return this.weakView != null && this.weakView.get() != null;
    }

    @Override
    public void attachView(V view) {
        this.weakView = new WeakReference<>(view);
        MVPViewInvocationHandler invocationHandler = new MVPViewInvocationHandler(this.weakView.get());
        proxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(), invocationHandler);
    }

    @Override
    public void detachView() {
        if (this.weakView != null) {
            this.weakView.clear();
            this.weakView = null;
        }
    }

    private class MVPViewInvocationHandler implements InvocationHandler {
        private MVPView mvpView;

        public MVPViewInvocationHandler(MVPView mvpView) {
            this.mvpView = mvpView;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws InvocationTargetException, IllegalAccessException {
            if (isAttachedView()) {
                return method.invoke(mvpView, objects);
            }
            return null;
        }
    }
}
