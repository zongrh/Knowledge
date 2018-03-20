package com.rxjava.FunctionOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rxjava.EstablishOperator.demo.GetRequest_Interface;
import com.rxjava.EstablishOperator.demo.Translation;
import com.rxjava.R;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zongrh on 2018/3/19.
 * All Rights Resered by HangZhou @2018-2019
 * 线程调度
 */

public class RxJavafixRetrofit2 extends Activity {
    private static final String TAG = "RxJava";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        //步骤4：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤5：创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //步骤6：采用Observable<...>形式 对网络请求 进行封装
        Observable<Translation> observable = request.getCall();

        //步骤7：发送网络请求
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");

                    }

                    @Override
                    public void onNext(Translation value) {
                        // 步骤8：对返回的数据进行处理
                        value.show() ;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "请求失败");

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "请求成功");

                    }
                });



    }
}
