package com.webviewjs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.webviewjs.js_android.method2.Js_webview_android_2;
import com.webviewjs.js_android.method3.Js_webview_android_3;
import com.webviewjs.js_android.method1.Js_webview_android_1;
import com.webviewjs.android_js.WebView_js1;
import com.webviewjs.android_js.WebView_js2;
import com.webviewjs.android_js.WebView_js3;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void test_main(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
//    JS通过WebView调用 Android 代码
//        mWebView.loadUrl("javascript:callJS()");
//        优点：方便简洁
//        缺点：效率低，获取返回值麻烦
//        使用场景：不需要返回值，对性能要求较低时
//        mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
//        优点：效率高
//        缺点：向下兼容性差（4.4以上可以用）
//        使用场景：4.4以上系统可用

    //    方式1：通过WebView的loadUrl（）
    public void webview_js_1(View view) {
        startActivity(new Intent(this, WebView_js1.class));
    }

    //    方式2：通过WebView的evaluateJavascript（）
    public void webview_js_2(View view) {
        startActivity(new Intent(this, WebView_js2.class));
    }

    //    方式3：两种方法混合使用，即Android 4.4以下使用方法1，Android 4.4以上方法2
    public void webview_js_3(View view) {
        startActivity(new Intent(this, WebView_js3.class));
    }


    public void js_webview_android_1(View view) {
        startActivity(new Intent(this, Js_webview_android_1.class));

    }

    public void Js_webview_android_2(View view) {
        startActivity(new Intent(this, Js_webview_android_2.class));
    }

    public void Js_webview_android_3(View view) {
        startActivity(new Intent(this, Js_webview_android_3.class));

    }
}
