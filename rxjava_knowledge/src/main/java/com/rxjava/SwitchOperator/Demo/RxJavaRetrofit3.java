package com.rxjava.SwitchOperator.Demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rxjava.R;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zongrh on 2018/3/20.
 * All Rights Resered by HangZhou @2018-2019
 */

public class RxJavaRetrofit3 extends Activity {
    private static final String TAG = "RxJava";

    // 定义Observable接口类型的网络请求对象
    Observable<Translation1> observable1;
    Observable<Translation2> observable2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

//        步骤一：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")//设置网络请求的 URL
                .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析（记得添加依赖）
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava
                .build();
//        步骤二：创建 网络请求 接口的实例
        GetRequest_Interface2 request2 = retrofit.create(GetRequest_Interface2.class);

//        步骤三：采用Observable<...>形式 对2哥网络请求进行封装
        observable1 = request2.getCall();
        observable2 = request2.getCall2();

        observable1.subscribeOn(Schedulers.io()) //（初始化被观察者）切换回IO线程进行网络请求1
                .observeOn(AndroidSchedulers.mainThread())//（新观察者）切换回主线程 处理网络请求1的结果
                .doOnNext(new Consumer<Translation1>() {
                    @Override
                    public void accept(Translation1 translation1) throws Exception {
                        Log.d(TAG, "第1次网络请求成功");
                        translation1.show(); // 对第1次网络请求返回的结果进行操作 = 显示翻译结果
                    }
                })
                .observeOn(Schedulers.io())
                //(新被观察者，同时也是新观察者) 切换到IO线程发起登录请求
                //特比注意flatMap 是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
                // 但对于初始观察者，他则是新的被观察者
                .flatMap(new Function<Translation1, ObservableSource<Translation2>>() {//做变换，即嵌套网络请求
                    @Override
                    public ObservableSource<Translation2> apply(Translation1 translation1) throws Exception {
//                        将网络请求1 转换成网络请求2，即发送网络请求2
                        return observable2;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())// （初始观察者）切换到主线程 处理网络请求2的结果
                .subscribe(new Consumer<Translation2>() {
                    @Override
                    public void accept(Translation2 translation2) throws Exception {
                        Log.e(TAG, "第二次网络请求成功");
                        translation2.show();// 对第2次网络请求返回的结果进行操作 = 显示翻译结果
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "登录失败");
                    }
                });


    }
}
