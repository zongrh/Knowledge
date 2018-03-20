package com.rxjava.SwitchOperator.Demo;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zongrh on 2018/3/20.
 * All Rights Resered by HangZhou @2018-2019
 */

public interface GetRequest_Interface2 {
    //网络请求一
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Observable<Translation1> getCall();

    //    网络请求二
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<Translation2> getCall2();

//    注解里传入网络请求的部分URL地址
//    Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里边
//    另一部分放在网络请求接口里边
//    如果接口是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
//    采用Observable<...>接口
//    getCall()是接手网络请求数据的方法

}
