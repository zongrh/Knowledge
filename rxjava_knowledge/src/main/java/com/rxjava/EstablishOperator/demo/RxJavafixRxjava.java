package com.rxjava.EstablishOperator.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rxjava.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zongrh on 2018/3/19.
 * All Rights Resered by HangZhou @2018-2019
 * 实战系列：无条件轮询
 */

public class RxJavafixRxjava extends Activity {
    private static final String TAG = "RxJava";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        /**
         * 步骤一：采用interval() 延迟发送
         */
        //参数说明：
        //参数1 = 第一次延迟时间
        //参数2 = 间隔时间数字
        //参数3 = 时间单位；
        // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
        Observable.interval(2, 5, TimeUnit.SECONDS)
                 /*
                  * 步骤二：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                  * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                  **/
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "第 " + aLong + " 次轮询");
                        /**
                         * 步骤三：通过Retrofit发送网络请求
                         */
//                        a.创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://fy.iciba.com/")//设置网路请求 Url
                                .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析(记得加入依赖)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava
                                .build();
//                        b.创建 网络请求接口 的实例
                        GetRequest_Interface request_interface = retrofit.create(GetRequest_Interface.class);

//                        c.采用Observable<...>形式 对网络进行请求 进行封装
                        Observable<Translation> observable = request_interface.getCall();
                        observable.subscribeOn(Schedulers.io())//切换到IO主线程进行网络请求
                                .observeOn(AndroidSchedulers.mainThread())//切换回到主线程 处理请求结果
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Translation value) {
                                        //e.接收服务器返回的数据
                                        value.show();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG, "请求失败");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });


    }


}
