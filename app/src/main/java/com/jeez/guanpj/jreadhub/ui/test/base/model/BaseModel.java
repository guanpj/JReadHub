package com.jeez.guanpj.jreadhub.ui.test.base.model;

import android.content.Context;

import com.jeez.guanpj.mvpframework.base.model.MVPBaseModel;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseModel extends MVPBaseModel {
    private Context context;

    public BaseModel(Context context){
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public String getServerUrl(){
        return "http://api.budejie.com/api/";
    }

    public <T> T buildService(Class<T> services) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getServerUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(services);
    }
}
