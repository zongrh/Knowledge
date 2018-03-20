package com.rxjava.CombineOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rxjava.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;


/**
 * Created by Administrator on 2018/3/15.
 * 从磁盘/内存缓存中 获取缓存数据
 */

public class UsageDemo1 extends Activity {
//    该两个变量用于模拟内存缓存 & 磁盘缓存中的数据
    String memoryCache = null;
    String diskCache = "从磁盘缓存中获取数据";
    private String TAG = "RxJava";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine3);

        /**
         * 设置第一个Observable：检查内存缓存是否有该数据的缓存
         */
        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                先判断内存缓存有无数据
                if (memoryCache != null) {
//                    若有该数据，则发送
                    e.onNext(memoryCache);
                } else {
//                 若无该数据 ， 则直接发送结束事件
                    e.onComplete();
                }
            }
        });


        /**
         * 设置第二个Observable：检查磁盘缓存是否有该数据的缓存
         */
        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                先判断磁盘缓存有无数据
                if (diskCache != null) {
//                    若有该数据，则发送
                    e.onNext(diskCache);
                } else {
//                    若无该数据，则直接发送结束事件
                    e.onComplete();
                }
            }
        });

        /**
         * 设置第三个 Observable:通过网络获取数据
         */
        Observable<String> network = Observable.just("从网络中获取数据");


        /**
         * 通过caoncat() 和 firstElement() 操作符实现缓存功能
         */
//        1、通过concat() 合并memory、disk、network三个被观察者的事件
//           （即检查内存缓存、磁盘缓存、 & 发送网络请求）
//        并将它们按顺序串联成队列
        Observable.concat(memory, disk, network)
                // 2. 通过firstElement()，从串联队列中取出并发送第1个有效事件（Next事件），
                // 即依次判断检查memory、disk、network
                .firstElement()
//                即本例的逻辑为：
                //a. firstElement()取出第一个事件=memory，即先判断内存缓存有无数据缓存；
                // 由于memoryCache=null，即内存缓存中无数据，所以发送结束事件（即为无效事件）
                // b. firstElement()取出第二个事件=disk ，即判断磁盘缓存中有无数据缓存；
                // 由于diskCache ≠ null，即磁盘缓存中有数据，所以发送Next事件（有效事件）
                // c. 即firstElement()已发出第1个有效事件（disk事件），所以停止判断。

//                3. 观察者订阅
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "最终获取的数据来源=  " + s);

                    }
                })
        ;



    }
}
