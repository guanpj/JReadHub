package com.jeez.guanpj.jreadhub.moduls.test.model;

import com.jeez.guanpj.jreadhub.moduls.test.bean.PostModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ITestService {

    @GET("api_open.php?a=list&c=data")
    Call<PostModel> getTestList(@Query("page") int page, @Query("type") int type);

    @GET("api_open.php?a=list&c=data")
    Observable<PostModel> getLCETestList(@Query("page") int page, @Query("type") int type);
}
