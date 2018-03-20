package com.rxjava.CombineOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rxjava.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zongrh on 2018/3/15.
 * All Rights Resered by HangZhou @2018-2019
 * 合并数据源 & 统一展示
 */

public class UsageDemo2 extends Activity {
    private String TAG = "RxJava_UsageDemo2";
    //    用于存放最终展示的数据
    private String result = "数据源来自 = ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine3);

        /**
         * 设置第一个Observable：通过网络获取数据
         * 此处仅作为网络请求的模拟
         */
        Observable<String> network = Observable.just("网络");

        /**
         * 设置第二个Observable：通过本地文件获取数据
         * 此处仅作本地文件请求的模拟
         */
        Observable<String> file = Observable.just("本地文件");

        /**
         * 通过merge() 合并事件 & 同时发送事件
         */
        Observable.merge(network, file)
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "数据源有： " + value);
                result += value + "+";
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            // 接手合并事件后，统一展示
            @Override
            public void onComplete() {
                Log.d(TAG, "获取数据完成");
                Log.d(TAG, result);
            }
        });





    }
}
