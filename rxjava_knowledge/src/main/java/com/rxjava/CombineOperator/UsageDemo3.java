package com.rxjava.CombineOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.rxjava.R;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * Created by zongrh on 2018/3/15.
 * All Rights Resered by HangZhou @2018-2019
 * 联合判断
 */

public class UsageDemo3 extends Activity {
    private String TAG = "RxJava";
    /**
     * 步骤1：设置控件变量  & 绑定
     */
    EditText name, age, job;
    Button list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine3);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        job = findViewById(R.id.job);
        list = findViewById(R.id.list);

/**
 *步骤2：为每个EditText设置被观察者，用于发送监听事件
 * 说明：
 * 1、此处采用RxBing,需引入依赖 implementation  'com.jakewharton.rxbinding2:rxbinding:2.0.0'
 * 2、传入EditText控件，点击任一个EditText撰写时，都会发送事件Function3()的返回值（下面会详细说明）
 * 3、采用skip(1) 原因：跳过 一开始 EditText 无任何输入时的空值
 */
        Observable<CharSequence> nameObservable = RxTextView.textChanges(name).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(age).skip(1);
        Observable<CharSequence> jobObservable = RxTextView.textChanges(job).skip(1);

        /**
         * 步骤3：通过combineLatest()合并事件 & 联合判断
         */
        Observable.combineLatest(nameObservable, ageObservable, jobObservable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) throws Exception {
                /**
                 * 步骤4：规定表单信息输入不能为空
                 */
//                1. 姓名信息
//                boolean isUserNameValid = !TextUtils.isEmpty(name.getText());
                boolean isUserNameValid = !TextUtils.isEmpty(name.getText()) && (name.getText().toString().length() > 2 && name.getText().toString().length() < 9);

//                2. 年龄信息
                boolean isUserAgeValid = !TextUtils.isEmpty(job.getText());
//                3. 职业信息
                boolean isUserJobValid = !TextUtils.isEmpty(job.getText());

                /**
                 * 步骤5：返回信息=联合判断 ，即三个信息同时填写，“提交按钮”才可以点击
                 */
                return isUserNameValid && isUserAgeValid && isUserJobValid;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                /**
                 * 步骤6：返回结果 & 设置按钮可点击样式
                 */
                Log.e(TAG, "提交按钮是否可点击" + aBoolean);
                list.setEnabled(aBoolean);

            }
        });

    }


}
