package com.zon.wanandroid.ui.base;

import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;
import com.zon.wanandroid.R;
import com.zon.wanandroid.app.App;
import com.zon.wanandroid.widget.CustomDialog;

import butterknife.ButterKnife;

/**
 * Created by Zon on 2018/3/25 0025.
 * All Rights Resered by HangZhou @2018-2019
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AutoLayoutActivity {
    protected T mPresenter;
    private CustomDialog mDialogWaiting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.activities.add(this);

        init();
//        判断是否使用了MVP模式
        mPresenter = createPresenter();
        if (mPresenter != null) {
            //因为之后所有的子类都要实现对应的View接口
            mPresenter.attachView((V) this);
        }
        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        setContentView(provideContentViewId());
        ButterKnife.bind(this);

        excuteStatesBar();

        initView();
        initData();
        initListener();
    }

    public void initListener() {
    }

    public void initData() {
    }

    public void initView() {
    }


    //activity窗口样式  在setContentView()调用之前调用，可以设置WindowFeature(如：this.requestWindowFeature(Window.FEATURE_NO_TITLE);)
    public void init() {

    }
    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();
    /**
     * 解决4.4设置状态栏颜色之后，布局内容嵌入状态栏位置问题
     */
    private void excuteStatesBar(){
        ViewGroup mContentView = (ViewGroup) getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows,
            // 而是设置 ContentView 的第一个子 View ，预留出系统 View 的空间.
            mChildView.setFitsSystemWindows(true);
        }
    }

    /**
     * 显示等待提示框
     */
    public Dialog showWaitingDialog(String tip) {
        hideWaitingDialog();
        View view = View.inflate(this, R.layout.dialog_waiting, null);
        if (!TextUtils.isEmpty(tip))
            ((TextView) view.findViewById(R.id.tvTip)).setText(tip);
        mDialogWaiting = new CustomDialog(this, view, R.style.MyDialog);
        mDialogWaiting.show();
        mDialogWaiting.setCancelable(false);
        return mDialogWaiting;
    }

    /**
     * 隐藏等待提示框
     */
    public void hideWaitingDialog(){
        if (mDialogWaiting != null) {
            mDialogWaiting.dismiss();
            mDialogWaiting = null;
        }
    }
}
