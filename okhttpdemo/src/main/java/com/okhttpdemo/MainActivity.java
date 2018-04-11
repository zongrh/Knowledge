package com.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getOK();
//        getOK2();

//        postJson();
    }

    private void postJson() {

    }

    private void getOK2() {
        String url = "http://write.blog.csdn.net/postlist/0/0/enabled/1";

        OkHttpClient mHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        /* okhttp3.Response response = null;*/
         /*response = mHttpClient.newCall(request).execute();*/
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("okHttp", "--- " + json);
            }
        });

    }

    /**
     * 异步     非UI线程
     */
    private void getOK() {
        String url = "https://github.com/hongyangAndroid";
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
//创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
//new call
        Call call = mOkHttpClient.newCall(request);

        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String htmlStr =  response.body().string();
                Log.e("okHttp", "htmlStr--- " + htmlStr);

            }
        });

    }
}
