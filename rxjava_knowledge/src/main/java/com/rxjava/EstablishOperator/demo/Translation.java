package com.rxjava.EstablishOperator.demo;

import android.util.Log;

/**
 * Created by zongrh on 2018/3/19.
 * All Rights Resered by HangZhou @2018-2019
 */

public class Translation {
    private int status;

    private content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.d("RxJava", content.out );
    }
}
