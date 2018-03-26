package com.zon.wanandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Zon on 2018/3/25 0025.
 * All Rights Resered by HangZhou @2018-2019
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
//        判断是否使用MVP模式
        mPresenter = createPresenter();
        if (mPresenter != null) {
//            因为之后所有的子类都熬实现对应的View接口
            mPresenter.attachView((V) this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @io.reactivex.annotations.Nullable ViewGroup container, @io.reactivex.annotations.Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(provideContentViewId(), container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@io.reactivex.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    public void initListener() {

    }

    public void initData() {
    }


    public void initView(View view) {
    }

    ;

    public void init() {
    }

    //用于创建Presenter和判断是都使用MVP模式（由子类实现）
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

}
