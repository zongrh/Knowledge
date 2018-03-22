package com.rxjava.SwitchOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.rxjava.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by zongrh on 2018/3/20.
 * All Rights Resered by HangZhou @2018-2019
 */

public class SwitchUsage extends Activity {

    private String TAG = "RxJava";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
//        test1();
//        test2();
//        test3();
        test4();

    }

    /**
     * MAP
     */
    private void test4() {
        //采用RxJava基于事件流的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 被观察者发送事件 = 参数为整型 = 1、2、3
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
            // 2. 使用Map变换操作符中的Function函数对被观察者发送的事件进行统一变换：整型变换成字符串类型
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "使用Map 变换操作符 将事件" + integer + "的参数从整形" + integer + " 变换成 字符串类型" + integer ;
            }
        }).subscribe(new Consumer<String>() {
            // 3. 观察者接收事件时，是接收到变换后的事件 = 字符串类型
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });


    }

    /**
     * flatMap
     */
    private void test3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + " 拆分后的子事件 " + i);
                    //通过flatMap中将被观察者生产的事件序列进行拆分，
                    //再将每个事件转换为一个新的发送三个Stirng事件
                    //最终合并，在发送给观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    /**
     * ConcatMap
     */
    private void test2() {
//        采用RxJava 基于事件流的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + " 拆分后的子事件 " + i);
                    //通过concatMap中将被观察者生产的事件序列先进行拆分，
                    //再将每个事件转换为一个新的发送三个String事件
                    //最终合并，在发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });


    }

    /**
     * Buffer()
     */
    private void test1() {
//        被观察者 需要发送五个数字
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1)//设置缓存区大小 & 步长
                //缓存区大小 = 每次从被观察者中获取的事件数量
                //步长= 每次获取新事件的数量
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> value) {
                        Log.e(TAG, "缓存区里的事件数量= " + value.size());
                        for (Integer integer : value) {
                            Log.e(TAG, "事件 = " + value);
                        }
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
