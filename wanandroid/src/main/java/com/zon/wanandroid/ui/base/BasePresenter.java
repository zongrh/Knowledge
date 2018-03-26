package com.zon.wanandroid.ui.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Zon on 2018/3/25 0025.
 * All Rights Resered by HangZhou @2018-2019
 * BasePresenter：主持者
 * WeakReference 弱引用
 * 定义：弱引用，与强引用（我们常见的引用方式）相对；
 * 特点是：GC在回收时会忽略掉弱引用对象（忽略掉这种引用关系），
 * 即：就算弱引用指向了某个对象，但只要该对象没有被强引用指向，
 * 该对象也会被GC检查时回收掉。
 */
public abstract class BasePresenter<V> {

    protected Reference<V> mViewRef;

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }
    public void detachView(){
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

}
