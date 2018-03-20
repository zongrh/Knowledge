package com.rxjava.FunctionOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rxjava.EstablishOperator.demo.GetRequest_Interface;
import com.rxjava.EstablishOperator.demo.Translation;
import com.rxjava.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zongrh on 2018/3/19.
 * All Rights Resered by HangZhou @2018-2019
 * 有条件轮询
 */

public class RxJavafixRetrofit extends Activity {
    private static final String TAG = "RxJava";
    //    设置变量 = 模拟轮询服务器次数
    private int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        //步骤一：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")//设置网络请求 Url
                .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析（记得添加依赖）
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava
                .build();
        //步骤二：创建 网络请求接口 的实例
        GetRequest_Interface request_interface = retrofit.create(GetRequest_Interface.class);
//        步骤三：采用Observable<...>形式对 网络请求 进行封装
        Observable<Translation> observable = request_interface.getCall();
//        步骤四：发送网络请求 & 通过repeatWhen()进行轮询
        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                // 此处有2种情况：
                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                        if (i > 3) {
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Translation>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Translation value) {
                // e.接收服务器返回的数据
                value.show() ;
                i++;
            }

            @Override
            public void onError(Throwable e) {
                // 获取轮询结束信息
                Log.d(TAG,  e.toString());
            }

            @Override
            public void onComplete() {

            }
        });


    }
}
