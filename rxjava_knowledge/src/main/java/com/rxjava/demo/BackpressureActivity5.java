package com.rxjava.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rxjava.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zongrh on 2018/3/21.
 * All Rights Resered by HangZhou @2018-2019
 */

public class BackpressureActivity5 extends Activity {
    private String TAG = "RxJava";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.back_layout);
//--------------------------------------------Flowable的基础使用讲解完
//        Flowable 的基本使用
//        test();
//        更加优雅的链式调用
//        test1();


//--------------------------------------------背压策略的使用
//        控制 观察者接收事件 的速度         异步订阅情况
//        test2();
//        观察者不接收事件的情况下，被观察者继续发送事件 & 存放到缓存区；再按需取出
//        test3();
//        观察者不接收事件的情况下，被观察者继续发送事件至超出缓存区大小（128）
//        test4();

//        同步订阅情况
//        同步订阅 & 异步订阅 的区别在于：
//        同步订阅中，被观察者 & 观察者工作于同1线程
//        同步订阅关系中没有缓存区

//        被观察者在发送1个事件后，必须等待观察者接收后，才能继续发下1个事件
//        test5();
//        实际上并不会出现被观察者发送事件速度 > 观察者接收事件速度的情况。可是，
//        却会出现被观察者发送事件数量 > 观察者接收事件数量的问题。
//        如：观察者只能接受3个事件，但被观察者却发送了4个事件，所以出现了不匹配情况
//        test6();
//        test7();

//--------------------------------------------控制 被观察者发送事件 的速度
//        同步订阅情况
//        下面的例子 = 被观察者根据观察者自身接收事件能力（10个事件），从而仅发送10个事件
//        test8();
//        情况1：可叠加性：观察者可连续要求接收事件，被观察者会进行叠加并一起发送
//        test9();
//        情况2：实时更新性: 每次发送事件后，emitter.requested()会实时更新观察者能接受的事件
//        即一开始观察者要接收10个事件，发送了1个后，会实时更新为9个
//        仅计算Next事件，complete & error事件不算。
//        test10();
//        情况3：异常:
//        当FlowableEmitter.requested()减到0 时，则代表观察者已经不可接收事件
//        此时被观察者若继续发送事件，则会抛出MissingBackpressureException异常
//        test11();
//        额外
//        若观察者没有设置可接收事件数量，即无调用Subscription.request（）
//        那么被观察者默认观察者可接收事件数量 = 0，即FlowableEmitter.requested()的返回值 = 0

//        异步订阅情况
//        FlowableEmitter.requested()知道观察者自身接收事件能力，
//        即 被观察者不能根据 观察者自身接收事件的能力 控制发送事件的速度。
//        test12();
//        而在异步订阅关系中，反向控制的原理是：通过RxJava内部固定调用被观察者线程中的request(n)
//        从而 反向控制被观察者的发送事件速度
//        那么该什么时候调用被观察者线程中的request(n) & n 的值该是多少呢？请继续往下看。
//        具体使用 关于RxJava内部调用request(n)（n = 128、96、0）的逻辑如下：
//        test13();
//--------------------------------------------采用背压策略模式：BackpressureStrategy
//        在Flowable的使用中，会被要求传入背压模式参数
//        面向对象：针对缓存区
//        作用：当缓存区大小存满、被观察者仍然继续发送下1个事件时，该如何处理的策略方式
//        缓存区大小存满、溢出 = 发送事件速度 ＞ 接收事件速度 的结果 = 发送 & 接收事件不匹配的结果
//        test14();
//        背压模式类型：
//        需要解决的问题：流速不匹配，即发送事件速度>接收事件速度
//        具体表现：当缓存区大小存满（默认缓存区大小128）、被观察者仍然继续发送下一个事件时

//        模式解决方案：
//        模式一：BackpressureStrategy.ERROR
//        问题：发送事件速度 ＞ 接收事件 速度，即流速不匹配
//        具体表现：出现当缓存区大小存满（默认缓存区大小 = 128）、被观察者仍然继续发送下1个事件时
//        处理方式：直接抛出异常MissingBackpressureException
//        test15();
//        模式2：BackpressureStrategy.MISSING
//        问题：发送事件速度 ＞ 接收事件 速度，即流速不匹配
//        具体表现是：出现当缓存区大小存满（默认缓存区大小 = 128）、被观察者仍然继续发送下1个事件时
//        处理方式：友好提示：缓存区满了
//        test16();
//        模式3：BackpressureStrategy.BUFFER
//        问题：发送事件速度 ＞ 接收事件 速度，即流速不匹配
//        具体表现是：出现当缓存区大小存满（默认缓存区大小 = 128）、被观察者仍然继续发送下1个事件时
//        处理方式：将缓存区大小设置成无限大
//        1、即 被观察者可无限发送事件 观察者，但实际上是存放在缓存区
//        2、但要注意内存情况，防止出现OOM
//        test17();
//        模式4： BackpressureStrategy.DROP
//        问题：发送事件速度 ＞ 接收事件 速度，即流速不匹配
//        具体表现是：出现当缓存区大小存满（默认缓存区大小 = 128）、被观察者仍然继续发送下1个事件时
//        处理方式：超过缓存区大小（128）的事件丢弃
//        如发送了150个事件，仅保存第1 - 第128个事件，第129 -第150事件将被丢弃
//        test18();
//        模式5：BackpressureStrategy.LATEST
//        问题：发送事件速度 ＞ 接收事件 速度，即流速不匹配
//        具体表现是：出现当缓存区大小存满（默认缓存区大小 = 128）、被观察者仍然继续发送下1个事件时
//        处理方式：只保存最新（最后）事件，超过缓存区大小（128）的事件丢弃
//        即如果发送了150个事件，缓存区里会保存129个事件（第1-第128 + 第150事件）
//        1、被观察者一下子发送了150个事件，点击按钮接收时观察者接收了128个事件；
//        2、再次点击接收时却接收到1个事件（第150个事件），这说明超过缓存区大小的事件仅保留最后的事件（第150个事件）
//        test19();
//        特别注意
//        在使用背压策略模式的时候，有1种情况是需要注意的：
//        a. 背景
//        FLowable 可通过自己创建（如上面例子），或通过其他方式自动创建，如interval操作符
//                interval操作符简介
//        作用：1、每隔1段时间就产生1个数字（Long型），从0开始、1次递增1，直至无穷大
//              2、默认运行在1个新线程上
//              3、与timer操作符区别：timer操作符可结束发送
//        b. 冲突
//        对于自身手动创建FLowable的情况，可通过传入背压模式参数选择背压策略（即上面描述的）
//        可是对于自动创建FLowable，却无法手动传入传入背压模式参数，那么出现流速不匹配的情况下，该如何选择 背压模式呢？
//        test20();
//        c. 解决方案
//        RxJava 2.0内部提供 封装了背压策略模式的方法
//        onBackpressureBuffer()
//        onBackpressureDrop()
//        onBackpressureLatest()
//        默认采用BackpressureStrategy.ERROR模式
        test21();
    }

    private void test21() {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer() // 添加背压策略封装好的方法，此处选择Buffer模式，即缓存区大小无限制
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: " + aLong);
                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test20() {
// 通过interval自动创建被观察者Flowable
        // 每隔1ms将当前数字（从0开始）加1，并发送出去
        // interval操作符会默认新开1个新的工作线程
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread()) // 观察者同样工作在一个新开线程中
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        s.request(Long.MAX_VALUE); //默认可以接收Long.MAX_VALUE个事件
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: " + aLong);
                        try {
                            Thread.sleep(1000);
                            // 每次延时1秒再接收事件
                            // 因为发送事件 = 延时1ms，接收事件 = 延时1s，出现了发送速度 & 接收速度不匹配的问题
                            // 缓存区很快就存满了128个事件，从而抛出MissingBackpressureException异常，请看下图结果
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    public void test19(View view) {
        mSubscription.request(128);
        // 每次接收128个事件
    }
    private void test19() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0;i< 150; i++) {
                    Log.d(TAG, "发送了事件" + i);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.LATEST) // // 设置背压模式 = BackpressureStrategy.LATEST
                .subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        // 通过按钮进行接收事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    public void test18(View view) {
        mSubscription.request(128);
        // 每次接收128个事件
    }
    private void test18() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 发送150个事件
                for (int i = 0;i< 150; i++) {
                    Log.d(TAG, "发送了事件" + i);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.DROP)      // 设置背压模式 = BackpressureStrategy.DROP
                .subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        // 通过按钮进行接收事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test17() {
        // 创建被观察者Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                // 发送 129个事件
                for (int i = 1;i< 150; i++) {
                    Log.d(TAG, "发送了事件" + i);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER) // 设置背压模式 = BackpressureStrategy.BUFFER
                .subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
//        可以接收超过原先缓存区大小（128）的事件数量了
    }

    private void test16() {
// 创建被观察者Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                // 发送 129个事件
                for (int i = 0;i< 129; i++) {
                    Log.d(TAG, "发送了事件" + i);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.MISSING) // 设置背压模式 = BackpressureStrategy.MISSING
                .subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test15() {
        // 创建被观察者Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 129; i++) {
                    Log.d(TAG, "发送了事件" + i);
                    emitter.onNext(i);
                }
                Log.d(TAG, "发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)// 设置背压模式 = BackpressureStrategy.ERROR
                .subscribeOn(Schedulers.io())//设置被观察者在IO线程中运行
                .observeOn(AndroidSchedulers.mainThread())//设置观察者在主线程中运行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
//                        对比Observable传入的Disposable参数，Subscriber此处传入的参数= Subscription
//                        相同点：Subscription参数具备Disposable参数的作用，即Disposable.dispose()切断连接,
//                        同样的调用Subscription.cancel()切断连接
//                        不同点：Subscription增加了void request(long n)

                        s.request(3);
                        // 作用：决定观察者能够接收多少个事件
                        // 如设置了s.request(3)，这就说明观察者能够接收3个事件（多出的事件存放在缓存区）
                        // 官方默认推荐使用Long.MAX_VALUE，即s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });

    }

    private void test14() {
//        创建被观察者Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                Log.d(TAG, "发送了事件 1");
                emitter.onNext(1);

                Log.d(TAG, "发送了事件 2");
                emitter.onNext(2);
                Log.d(TAG, "发送了事件 3");
                emitter.onNext(3);
                Log.d(TAG, "发送了事件 4");
                emitter.onNext(4);

                Log.d(TAG, "发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)//设置背压模式
                .subscribeOn(Schedulers.io())//设置被观察者在IO线程中运行
                .observeOn(AndroidSchedulers.mainThread())//设置观察者在主线程中运行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
//                        对比Observable传入的Disposable参数，Subscriber此处传入的参数= Subscription
//                        相同点：Subscription参数具备Disposable参数的作用，即Disposable.dispose()切断连接,
//                        同样的调用Subscription.cancel()切断连接
//                        不同点：Subscription增加了void request(long n)

                        s.request(3);
                        // 作用：决定观察者能够接收多少个事件
                        // 如设置了s.request(3)，这就说明观察者能够接收3个事件（多出的事件存放在缓存区）
                        // 官方默认推荐使用Long.MAX_VALUE，即s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });

    }

    public void test13(View view) {
        mSubscription.request(48);
        // 点击按钮 则 接收48个事件
    }

    private void test13() {
// 被观察者：一共需要发送500个事件，但真正开始发送事件的前提 = FlowableEmitter.requested()返回值 ≠ 0
// 观察者：每次接收事件数量 = 48（点击按钮）
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "观察者可接收事件数量= " + e.requested());
                boolean flag;//设置标记控制
                for (int i = 0; i < 500; i++) {
                    flag = false;
                    while (e.requested() == 0) {
                        if (!flag) {
                            Log.e(TAG, "不再发送");
                            flag = true;
                        }
                    }
//                    requested() ≠ 0 才发送
                    Log.d(TAG, "被观察者  发送了事件" + i + "，观察者可接收事件数量 = " + e.requested());
                    e.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())//设置被观察者在IO线程中进行
                .observeOn(AndroidSchedulers.mainThread())//设置观察者在主线程中进行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        // 初始状态 = 不接收事件；通过点击按钮接收事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, " 观察者 ******** 接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    private void test12() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                // 调用emitter.requested()获取当前观察者需要接收的事件数量
                Log.d(TAG, "观察者可接收事件数量 = " + emitter.requested());

            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        s.request(150);
                        // 该设置仅影响观察者线程中的requested，却不会影响的被观察者中的FlowableEmitter.requested()的返回值
                        // 因为FlowableEmitter.requested()的返回值 取决于RxJava内部调用request(n)，而该内部调用会在一开始就调用request(128)
                        // 为什么是调用request(128)下面再讲解
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    private void test11() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                // 1. 调用emitter.requested()获取当前观察者需要接收的事件数量
                Log.d(TAG, "观察者可接收事件数量 = " + emitter.requested());

                // 2. 每次发送事件后，emitter.requested()会实时更新观察者能接受的事件
                // 即一开始观察者要接收10个事件，发送了1个后，会实时更新为9个
                Log.d(TAG, "发送了事件 1");
                emitter.onNext(1);
                Log.d(TAG, "发送了事件1后, 还需要发送事件数量 = " + emitter.requested());

                Log.d(TAG, "发送了事件 2");
                emitter.onNext(2);
                Log.d(TAG, "发送事件2后, 还需要发送事件数量 = " + emitter.requested());

                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                        Log.d(TAG, "onSubscribe");
                        s.request(1); // 设置观察者每次能接受1个事件

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test10() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                // 1. 调用emitter.requested()获取当前观察者需要接收的事件数量
                Log.d(TAG, "观察者可接收事件数量 = " + emitter.requested());

                // 2. 每次发送事件后，emitter.requested()会实时更新观察者能接受的事件
                // 即一开始观察者要接收10个事件，发送了1个后，会实时更新为9个
                Log.d(TAG, "发送了事件 1");
                emitter.onNext(1);
                Log.d(TAG, "发送了事件1后, 还需要发送事件数量 = " + emitter.requested());

                Log.d(TAG, "发送了事件 2");
                emitter.onNext(2);
                Log.d(TAG, "发送事件2后, 还需要发送事件数量 = " + emitter.requested());

                Log.d(TAG, "发送了事件 3");
                emitter.onNext(3);
                Log.d(TAG, "发送事件3后, 还需要发送事件数量 = " + emitter.requested());

                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");

                        s.request(10); // 设置观察者每次能接受10个事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test9() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                // 调用emitter.requested()获取当前观察者需要接收的事件数量
                long n = emitter.requested();

                Log.d(TAG, "观察者可接收事件 n " + n);

                // 根据emitter.requested()的值，即当前观察者需要接收的事件数量来发送事件
                for (int i = 0; i < n; i++) {
                    Log.d(TAG, "发送了事件" + i);
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");

                        s.request(10); // 第1次设置观察者每次能接受10个事件
                        s.request(20); // 第2次设置观察者每次能接受20个事件

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test8() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                // 调用emitter.requested()获取当前观察者需要接收的事件数量
                long n = emitter.requested();

                Log.d(TAG, "观察者可接收事件" + n);

                // 根据emitter.requested()的值，即当前观察者需要接收的事件数量来发送事件
                for (int i = 0; i < n; i++) {
                    Log.d(TAG, "发送了事件" + i);
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");

                        // 设置观察者每次能接受10个事件
                        s.request(10);

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test7() {
        //        步骤一：创建一个被观察者
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
//                发送事件
                e.onNext(0);
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR)//设置背压
//                创建观察者
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        // 不设置request（long n）
                        // s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件 " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test6() {
//        步骤一：创建一个被观察者
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
//                发送事件
                e.onNext(0);
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR)//设置背压
//                创建观察者
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        s.request(3);
                        // 观察者接收事件 = 3个 ，即不匹配
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件 " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test5() {
//        步骤一:创建被观察者Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
// 发送3个事件
                Log.d(TAG, "发送了事件1");
                emitter.onNext(1);
                Log.d(TAG, "发送了事件2");
                emitter.onNext(2);
                Log.d(TAG, "发送了事件3");
                emitter.onNext(3);
                emitter.onComplete();

            }
        }, BackpressureStrategy.ERROR)
//                步骤二：创建观察者 = Subscriber
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        s.request(3);
                        // 每次可接收事件 = 3 二次匹配
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件 " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test4() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 一共发送129个事件，即超出了缓存区的大小
                for (int i = 0; i < 129; i++) {
                    Log.d(TAG, "发送了事件" + i);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())//设置被观察者在IO线程中运行
                .observeOn(AndroidSchedulers.mainThread())//设置观察者在主线程
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        // 默认不设置可接收事件大小
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    /**
     * 步骤1：设置变量
     */
    private Subscription mSubscription; // 用于保存Subscription对象

    public void buttonFlowable(View view) {
        mSubscription.request(1);

    }

    private void test3() {

/**
 * 步骤3：异步调用
 */
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "发送事件 1");
                emitter.onNext(1);
                Log.d(TAG, "发送事件 2");
                emitter.onNext(2);
                Log.d(TAG, "发送事件 3");
                emitter.onNext(3);
                Log.d(TAG, "发送事件 4");
                emitter.onNext(4);
                Log.d(TAG, "发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        // 保存Subscription对象，等待点击按钮时（调用request(2)）观察者再接收事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void test2() {
//        1.创建被观察者Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 一共发送4个事件
                Log.d(TAG, "发送事件 1");
                emitter.onNext(1);
                Log.d(TAG, "发送事件 2");
                emitter.onNext(2);
                Log.d(TAG, "发送事件 3");
                emitter.onNext(3);
                Log.d(TAG, "发送事件 4");
                emitter.onNext(4);
                Log.d(TAG, "发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())//设置被观察者在IO线程
                .observeOn(AndroidSchedulers.mainThread())//设置观察者在UI主线程中进行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
//                        对比Observable传入的Disposable参数，Subscriber此处传入的参数= Subscription
//                        相同点：Subscription参数具备Disposable参数的作用，即Disposable.dispose()切断连接,
//                        同样的调用Subscription.cancel()切断连接
//                        不同点：Subscription增加了void request(long n)

                        s.request(3);
                        // 作用：决定观察者能够接收多少个事件
                        // 如设置了s.request(3)，这就说明观察者能够接收3个事件（多出的事件存放在缓存区）
                        // 官方默认推荐使用Long.MAX_VALUE，即s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "接收到了事件 " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    private void test1() {
//        步骤一：创建被观察者 = Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "发送事件 1");
                e.onNext(1);
                Log.d(TAG, "发送事件 2");
                e.onNext(2);
                Log.d(TAG, "发送事件 3");
                e.onNext(3);
                Log.d(TAG, "发送事件 34");
                e.onNext(4);
                Log.d(TAG, "发送完成");
                e.onComplete();

            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    //                    步骤二：创建观察者=Subscriber & 建立订阅关系
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    private void test() {
        /**
         * 步骤一：创建被观察者=Flowable
         */
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR);
//        需要传入被压参数BackpressureStrategy,下面会详解

        /**
         * 步骤二：创建被观察者=Subscriber
         */
        Subscriber<Integer> downstream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                // 相同点：Subscription具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接
                // 不同点：Subscription增加了void request(long n)
                Log.d(TAG, "onSubscribe");
                s.request(Long.MAX_VALUE);
                // 关于request()下面会继续详细说明

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
        /**
         * 步骤三：建立订阅关系
         */
        upstream.subscribe(downstream);

    }



}
