package com.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by zongrh on 2018/3/15.
 * All Rights Resered by HangZhou @2018-2019
 */

public class FiterUsage extends Activity {
    private String TAG = "RxJava";
    /**
     * 联想搜索优化
     */
    /**
     * 步骤一：设置控件白能量 & 绑定
     */
    EditText ed;
    TextView tv;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

//        控件绑定
        ed = findViewById(R.id.ed);
        tv = findViewById(R.id.tv);

        /**
         * 说明
         * 1. 此处采用了RxBinding: RxTextView.textChanges(name)=对对控件数据变更进行监听
         * （功能类似TextWatcher）,需要引入依赖  implementation  'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入EditText控件，输入字符时会发送数据事件（此处不会马上发送，因为使用了debounce()）
         * 3. 采用skip(1)原因：跳过 第1次请求 = 初始输入框的空字符状态
         */
        RxTextView.textChanges(ed)
                .debounce(2, TimeUnit.SECONDS)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharSequence value) {
                        tv.setText("发送给服务器的字符= " + value.toString());
                        Log.e(TAG, "对onNext事件作出了响应 输入内容： " + value.toString());
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Log.e(TAG, "时间 " + df.format(System.currentTimeMillis()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "对Error事件作出了响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "对Complete事件作出响应");
                    }
                });

        /**
         * 功能防抖
         */
//        注册控件
        button = findViewById(R.id.button);
        /**
         * 1.此处采用RxBinding: RxView.clicks(button)=对控件点击进行监听
         * 需引入依赖 implementation  'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2.传入Button控件，点击时，都会发送数据事件（但由于使用了throttleFirst（）操作符，
         * 所以只会发送该段时间内的第1次点击事件）
         */
        RxView.clicks(button)
                .throttleFirst(2, TimeUnit.SECONDS)//才发送 2s内第一次点击按钮的事件
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        Log.e(TAG, "发送了网络请求");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "对Error事件作出响应" + e.toString());
//                        获取异常错误信息

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "对Complete事件作出响应");
                    }
                });
    }
}
