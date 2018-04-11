package com.webviewjs.js_android.method1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.webviewjs.R;

//HTML通过JS显示Toast与普通列表的对话框
public class Js_webview_android_1 extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.js_webview_android);
        mWebView = findViewById(R.id.webview);

        mWebView.loadUrl("file:///android_asset/demo1.html");
        WebSettings webSettings = mWebView.getSettings();
        //①设置WebView允许调用js
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        //②将object对象暴露给Js,调用addjavascriptInterface
        mWebView.addJavascriptInterface(new MyObject(Js_webview_android_1.this), "myObj");

    }
}
