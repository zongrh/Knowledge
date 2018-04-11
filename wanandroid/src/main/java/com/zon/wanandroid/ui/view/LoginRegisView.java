package com.zon.wanandroid.ui.view;

import com.zon.wanandroid.model.pojo.UserBean;

/**
 * Created by Zon on 2018/4/3 0003.
 * All Rights Resered by HangZhou @2018-2019
 * desc: 登录注册
 */
public interface LoginRegisView {

    void showProgress(String tipString);

    void hideProgress();

    void loginSuccess(UserBean userBean);

    void registerSuccess(UserBean userBean);

    void loginFail();

    void registerFail();
}
