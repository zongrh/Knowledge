package com.webviewjs.js_android.method1;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.webviewjs.R;

/**
 * Created by Zon on 2018/4/11 0011.
 * All Rights Resered by HangZhou @2018-2019
 */
// 继承自Object类
public class MyObject {
    private Context context;
    public MyObject(Context context) {
        this.context = context;
    }

    //将显示Toast和对话框的方法暴露给JS脚本调用
    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void showToast(String name) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void showDialog() {
        new AlertDialog.Builder(context)
                .setTitle("联系人列表").setIcon(R.mipmap.ic_launcher)
                .setItems(new String[]{"基神", "B神", "曹神", "街神", "翔神"}, null)
                .setPositiveButton("确定", null).create().show();
    }
}