package com.zon.wanandroid.ui.view;

import com.zon.wanandroid.model.pojo.ArticleBean;
import com.zon.wanandroid.model.pojo.BannerBean;

import java.util.List;

/**
 * Created by Zon on 2018/3/26 0026.
 * All Rights Resered by HangZhou @2018-2019
 * 首页
 */
public interface HomeView {
    void showRefreshView(Boolean refresh);

    void getBannerDataSuccess(List<BannerBean> data);

    void getDataError(String message);

    void getRefreshDataSuccess(List<ArticleBean>data);

    void getMoreDataSuccess(List<ArticleBean> data);

}
