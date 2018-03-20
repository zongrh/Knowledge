package com.rxjava.EstablishOperator.demo;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zongrh on 2018/3/19.
 * All Rights Resered by HangZhou @2018-2019
 */

public interface GetRequest_Interface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();


}
