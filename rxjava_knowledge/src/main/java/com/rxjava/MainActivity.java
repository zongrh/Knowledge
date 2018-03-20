package com.rxjava;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rxjava.CombineOperator.UsageDemo1;
import com.rxjava.CombineOperator.UsageDemo2;
import com.rxjava.CombineOperator.UsageDemo3;
import com.rxjava.EstablishOperator.EstablishUsage;
import com.rxjava.EstablishOperator.demo.RxJavafixRxjava;
import com.rxjava.FunctionOperator.RxJavafixRetrofit;
import com.rxjava.FunctionOperator.RxJavafixRetrofit2;
import com.rxjava.FunctionOperator.RxJavafixRetrofit3;
import com.rxjava.SwitchOperator.Demo.RxJavaRetrofit3;
import com.rxjava.SwitchOperator.SwitchUsage;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void UsageDemo1(View view) {
        startActivity(new Intent(this, UsageDemo1.class));
    }

    public void UsageDemo2(View view) {
        startActivity(new Intent(this, UsageDemo2.class));
    }

    public void UsageDemo3(View view) {
        startActivity(new Intent(this, UsageDemo3.class));

    }

    public void FiterUsage(View view) {
        startActivity(new Intent(this, FiterUsage.class));
    }

    public void Commonknowledge(View view) {
        startActivity(new Intent(this, Commonknowledge.class));
    }

    public void EstablishUsage(View view) {
        startActivity(new Intent(this, EstablishUsage.class));
    }

    public void RxJavafixRxjava(View view) {
        startActivity(new Intent(this, RxJavafixRxjava.class));
    }


    public void RxJavafixRetrofit(View view) {
        startActivity(new Intent(this, RxJavafixRetrofit.class));

    }

    public void RxJavafixRetrofit2(View view) {
        startActivity(new Intent(this, RxJavafixRetrofit2.class));

    }

    public void RxJavafixRetrofit3(View view) {
        startActivity(new Intent(this, RxJavafixRetrofit3.class));


    }

    public void SwitchUsage(View view) {
        startActivity(new Intent(this, SwitchUsage.class));

    }

    public void RxJavaRetrofit3(View view) {
        startActivity(new Intent(this, RxJavaRetrofit3.class));
    }
}
