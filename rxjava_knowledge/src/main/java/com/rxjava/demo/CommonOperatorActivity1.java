package com.rxjava.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rxjava.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by zongrh on 2018/3/20.
 * All Rights Resered by HangZhou @2018-2019
 * 基础操作符详解--创建操作符
 */

public class CommonOperatorActivity1 extends Activity {
    private String TAG = "RxJava";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
//--------------------------------------------------- 快速创建 & 发送事件
//        create（）操作符：
//        作用：
//        完整创建1个被观察者对象（Observable）
//        RxJava 中创建被观察者对象最基本的操作符
//        test1();

//        just() 操作符：
//        作用：
//        快速创建1个被观察者对象（Observable）
//        发送事件的特点：直接发送 传入的事件
//        注：最多只能发送10个参数
//        应用场景：
//        快速创建 被观察者对象（Observable） & 发送10个以下事件
//        test2();

//        fromArray（）操作符：
//        快速创建1个被观察者对象（Observable）
//        发送事件的特点：直接发送 传入的数组数据
//        注：会将数组中的数据转换为Observable对象
//        应用场景：
//        快速创建 被观察者对象（Observable） & 发送10个以上事件（数组形式）
//        数组元素遍历
//        test3();
//        test3_2();
//        test3_3();

//        fromIterable（）
//        作用:
//        快速创建1个被观察者对象（Observable）
//        发送事件的特点：直接发送 传入的集合List数据
//        注：会将数组中的数据转换为Observable对象
//        应用场景:
//        快速创建 被观察者对象（Observable） & 发送10个以上事件（集合形式）
//        集合元素遍历
//        test4();

//        额外的方法
//<-- empty()  -->
// 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
//        Observable observable1 = Observable.empty();
// 即观察者接收后会直接调用onCompleted（）

//<-- error()  -->
// 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
// 可自定义异常
//        Observable observable2 = Observable.error(new RuntimeException());
// 即观察者接收后会直接调用onError（）

//<-- never()  -->
// 该方法创建的被观察者对象发送事件的特点：不发送任何事件
//        Observable observable3 = Observable.never();
// 即观察者接收后什么都不调用
//        test5();
//--------------------------------------------------- 延迟创建
//        需求场景
//        定时操作：在经过了x秒后，需要自动执行y操作
//        周期性操作：每隔x秒后，需要自动执行y操作

//        defer（）
//        作用：
//        直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件
//        注：1、通过 Observable工厂方法创建被观察者对象（Observable）
//            2、每次订阅后，都会得到一个刚创建的最新的Observable对象，这可以确保Observable对象里的数据是最新的
//        应用场景：
//        动态创建被观察者对象（Observable） & 获取最新的Observable对象数据
//        test6();

//        timer（）
//        作用
//        1、快速创建1个被观察者对象（Observable）
//        2、发送事件的特点：延迟指定时间后，发送1个数值0（Long类型）
//        注：本质 = 延迟指定时间后，调用一次 onNext(0)
//        应用场景
//        延迟指定事件，发送一个0，一般用于检测
//        test7();

//        interval（）
//        作用：
//        1、快速创建1个被观察者对象（Observable）
//        2、发送事件的特点：每隔指定时间 就发送 事件
//        注：发送的事件序列 = 从0开始、无限递增1的的整数序列
//        test8();


//        intervalRange（）
//        作用：
//        1、快速创建1个被观察者对象（Observable）
//        2、发送事件的特点：每隔指定时间 就发送 事件，可指定发送的数据的数量
//        注意： a. 发送的事件序列 = 从0开始、无限递增1的的整数序列
//               b. 作用类似于interval（），但可指定发送的数据的数量
//        test9();


//        range（）
//        作用：
//        1、快速创建1个被观察者对象（Observable）
//        2、发送事件的特点：连续发送 1个事件序列，可指定范围
//        注意：
//        a. 发送的事件序列 = 从0开始、无限递增1的的整数序列
//        b. 作用类似于intervalRange（），但区别在于：无延迟发送事件
        test10();


    }

    private void test10() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 注：若设置为负数，则会抛出异常
        Observable.range(3,10)
                // 该例子发送的事件序列特点：从3开始发送，每次发送事件递增1，一共发送10个事件
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
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

    private void test9() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 参数3 = 第1次事件延迟发送时间；
        // 参数4 = 间隔时间数字；
        // 参数5 = 时间单位
        Observable.intervalRange(3,10,2, 1, TimeUnit.SECONDS)
                // 该例子发送的事件序列特点：
                // 1. 从3开始，一共发送10个事件；
                // 2. 第1次延迟2s发送，之后每隔2秒产生1个数字（从0开始递增1，无限个）
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件"+ value  );
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

    private void test8() {
// 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        Observable.interval(3,1,TimeUnit.SECONDS)
                // 该例子发送的事件序列特点：延迟3s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件"+ value  );
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
// 注：interval默认在computation调度器上执行
// 也可自定义指定线程调度器（第3个参数）：interval(long,TimeUnit,Scheduler)

    }

    private void test7() {
// 该例子 = 延迟2s后，发送一个long类型数值
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
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

// 注：timer操作符默认运行在一个新线程上
// 也可自定义线程调度器（第3个参数）：timer(long,TimeUnit,Scheduler)

    }


    //<-- 1. 第1次对i赋值 ->>
    private Integer i = 10;

    private void test6() {
        // 2. 通过defer 定义被观察者对象
        // 注：此时被观察者对象还没创建

        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

//        <-- 2. 第2次对i赋值 ->>
        i = 15;

//        <-- 3. 观察者开始订阅 ->>
        // 注：此时，才会调用defer（）创建被观察者对象（Observable）
        observable.subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到的整数是 " + value);
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

    // 下列方法一般用于测试使用
    private void test5() {
//<-- empty()  -->
// 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
//        Observable observable1 = Observable.empty();
// 即观察者接收后会直接调用onCompleted（）

//<-- error()  -->
// 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
// 可自定义异常
//        Observable observable2 = Observable.error(new RuntimeException());
// 即观察者接收后会直接调用onError（）

//                <-- never()  -->
// 该方法创建的被观察者对象发送事件的特点：不发送任何事件
//        Observable observable3 = Observable.never();
// 即观察者接收后什么都不调用

    }

    private void test4() {
// 1. 设置一个集合
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(3);

        // 2. 通过fromIterable()将集合中的对象 / 数据发送出去
        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "集合遍历");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "集合中的数据元素 = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "遍历结束");
                    }
                });

    }

    private void test3_3() {
        // 1. 设置需要传入的数组
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        // 2. 创建被观察者对象（Observable）时传入数组
        // 在创建后就会将该数组转换成Observable & 发送该对象中的所有数据
        Observable.fromArray(list)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "数组遍历");
                    }

                    @Override
                    public void onNext(List<Integer> value) {
                        Log.d(TAG, "数组中的元素 = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "遍历结束");
                    }

                });
// 若直接传递一个list集合进去，会直接把list当做一个数据元素发送
    }

    private void test3_2() {

        // 1. 设置需要传入的数组
        Integer[] items = {0, 1, 2, 3, 4};

        // 2. 创建被观察者对象（Observable）时传入数组
        // 在创建后就会将该数组转换成Observable & 发送该对象中的所有数据
        Observable.fromArray(items)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "数组遍历");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "数组中的元素 = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "遍历结束");
                    }

                });
    }

    private void test3() {
// 1. 设置需要传入的数组
        Integer[] items = {0, 1, 2, 3, 4};

        // 2. 创建被观察者对象（Observable）时传入数组
        // 在创建后就会将该数组转换成Observable & 发送该对象中的所有数据
        Observable.fromArray(items)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
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


    private void test2() {
// 1. 创建时传入整型1、2、3、4
        // 在创建后就会发送这些对象，相当于执行了onNext(1)、onNext(2)、onNext(3)、onNext(4)
        Observable.just(1, 2, 3, 4)
                // 至此，一个Observable对象创建完毕，以下步骤仅为展示一个完整demo，可以忽略
                // 2. 通过通过订阅（subscribe）连接观察者和被观察者
                // 3. 创建观察者 & 定义响应事件的行为
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
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

    private void test1() {
// 1. 通过creat（）创建被观察者对象
        Observable.create(new ObservableOnSubscribe<Integer>() {

            // 2. 在复写的subscribe（）里定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);

                emitter.onComplete();
            }  // 至此，一个被观察者对象（Observable）就创建完毕
        }).subscribe(new Observer<Integer>() {
            // 以下步骤仅为展示一个完整demo，可以忽略
            // 3. 通过通过订阅（subscribe）连接观察者和被观察者
            // 4. 创建观察者 & 定义响应事件的行为
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);
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
