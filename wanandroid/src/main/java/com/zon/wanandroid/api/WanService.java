package com.zon.wanandroid.api;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okrx2.adapter.ObservableBody;
import com.readystatesoftware.chuck.internal.support.JsonConvertor;
import com.zon.wanandroid.app.AppConst;
import com.zon.wanandroid.helper.JsonConvert;
import com.zon.wanandroid.model.ResponseData;
import com.zon.wanandroid.model.pojo.BannerBean;
import com.zon.wanandroid.model.pojpVO.ArticleListVO;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zon on 2018/3/25 0025.
 * All Rights Resered by HangZhou @2018-2019
 * desc：玩Android提供的api接口
 * www.wanandroid.com
 */
public class WanService {
    private static String homeDataList = AppConst.BASE_URL + "article/list/{page}/json";
    private static String homeBannerData = AppConst.BASE_URL + "banner/json";
    private static String hotKeyUrl = AppConst.BASE_URL + "hotkey/json";
    private static String loginUrl = AppConst.BASE_URL + "user/login";
    private static String registUrl = AppConst.BASE_URL + "user/register";
    private static String getClollectData = AppConst.BASE_URL + "lg/collect/list/0/json";

    /**
     * 首页Banner
     *
     * @GET("/banner/json")
     */
    public static Observable<ResponseData<List<BannerBean>>> getBannerData() {
        return OkGo.<ResponseData<List<BannerBean>>>get(homeBannerData)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<ResponseData<List<BannerBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<BannerBean>>>());
    }

    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     *
     * @param page
     * @return
     */
    public static Observable<ResponseData<ArticleListVO>> getHomeData(int page) {
        String url = AppConst.BASE_URL + "article/list/" + page + "/json";
        return OkGo.<ResponseData<ArticleListVO>>get(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST) //使用缓存
                .converter(new JsonConvert<ResponseData<ArticleListVO>>() {
                })
                .adapt(new ObservableBody<>());
    }

}
