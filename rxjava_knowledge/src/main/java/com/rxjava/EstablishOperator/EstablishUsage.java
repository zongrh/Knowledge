package com.rxjava.EstablishOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rxjava.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


/**
 * Created by zongrh on 2018/3/19.
 * All Rights Resered by HangZhou @2018-2019
 * 建立使用
 * 应用场景 & 对应操作符介绍
 */

public class EstablishUsage extends Activity {
    private String TAG = "RxJava";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
        test7();


    }

    /**
     * 完整创建被观察者
     */
    private void test7() {
//        步骤一：通过create()创建完整的被观察者对象
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            // 2. 在复写的subscribe（）里定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });//至此，一个完整的被观察者对象创建完毕。

//        步骤二：创建观察者对象Observable 并 定义响应事件行为
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件 = "+ value   );

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");

            }
        };

//        步骤三：通过订阅subscribe连接观察者和被观察者
        observable.subscribe(observer);

    }

    /**
     * 定时操作
     */
//    该例子 = 延迟 2s 后，进行日志输出操作
    private void test6() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "onNext + "+value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "在2s后进行了该操作");

                    }
                });
        // 注：timer操作符默认运行在一个新线程上
        // 也可自定义线程调度器（第3个参数）：timer(long,TimeUnit,Scheduler)

    }

    /**
     * 周期性操作
     */
    private void test5() {
        // 该例子发送的事件序列特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个） = 每隔1s进行1次操作
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");

                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "每隔1s进行1次操作 - " + value);

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

    /**
     * 集合遍历
     */
    private void test4() {
        // 1. 设置一个集合
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
//        2. 通过fromIterable()将集合中的对象 /数据发送出去
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

    /**
     * 数组遍历
     */
    private void test3() {
//        1. 设置要传入的数组
        Integer[] item = {0, 1, 2, 3, 4};
//        2. 创建被观察者对象（Observable）时传入数组
//        在创建后就将该数组换成Observable & 发送该对象的所you数据
        Observable.fromArray(item)
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
                        Log.d(TAG, "遍历数组结束");

                    }
                });


    }

    //具体使用
    private void test2() {
        Observable.just(1, 2, 4)
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return Observable.just(1);
                    }
                }).subscribe(new Observer<Integer>() {
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

    private void test1() {
        Observable.just(1, 2, 4).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable
                // 此处有2种情况：
                // 1. 若新被观察者（Observable）返回1个Complete事件，则不重新订阅 & 发送原来的 Observable
                // 2. 若新被观察者（Observable）返回1个Next事件，则重新订阅 & 发送原来的 Observable
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 情况1：若新被观察者（Observable）返回1个Complete事件，则不重新订阅 & 发送原来的 Observable
                        return Observable.empty();
                        // 返回Error
//                        return Observable.error(new Throwable("不再重新订阅事件"));

                        // 情况2：若新被观察者（Observable）返回1个Next事件，则重新订阅 & 发送原来的 Observable
                        // return Observable.just(1);
                    }
                });
            }

        }).subscribe(new Observer<Integer>() {
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
                Log.d(TAG, "对Error事件作出响应：" + e.toString());

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

}
