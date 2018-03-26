package com.zon.wanandroid.helper.rxjavahelper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zon on 2018/3/26 0026.
 * All Rights Resered by HangZhou @2018-2019
 * desc：compose()里接收一个Transformer对象，ObservableTransformer
 *       可以通过它将一种类型的Observable转换成另一种类型的Observable。
 *       现在.subscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread())
 *       的地方可以用.compose(RxSchedulersHelper.io_main())代替。
 */
public class RxSchedulersHelper {
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
