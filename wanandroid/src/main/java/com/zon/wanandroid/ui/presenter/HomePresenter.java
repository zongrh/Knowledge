package com.zon.wanandroid.ui.presenter;

import android.util.Log;

import com.zon.wanandroid.api.WanService;
import com.zon.wanandroid.helper.rxjavahelper.RxObserver;
import com.zon.wanandroid.helper.rxjavahelper.RxResultHelper;
import com.zon.wanandroid.helper.rxjavahelper.RxSchedulersHelper;
import com.zon.wanandroid.model.pojo.BannerBean;
import com.zon.wanandroid.model.pojpVO.ArticleListVO;
import com.zon.wanandroid.ui.base.BasePresenter;
import com.zon.wanandroid.ui.view.HomeView;
import com.zon.wanandroid.util.PrefUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Zon on 2018/3/26 0026.
 * All Rights Resered by HangZhou @2018-2019
 * desc：首页
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private int mCurrentPage;

    //    获取轮播图数据
    public void getBannerData() {
        WanService.getBannerData()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<BannerBean>>() {
                    @Override
                    public void _onNext(List<BannerBean> beans) {
                        getView().getBannerDataSuccess(beans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }
                });
    }


    //    刷新
    public void getRefreshData() {
        mCurrentPage = 0;
        WanService.getHomeData(mCurrentPage)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ArticleListVO>() {
                    @Override
                    public void _onSubscribe(Disposable d) {
                        getView().showRefreshView(true);
                    }

                    @Override
                    public void _onNext(ArticleListVO vo) {
                        getView().getRefreshDataSuccess(vo.getDatas());
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }

                    @Override
                    public void _onComplete() {
                        getView().showRefreshView(false);
                    }
                });
    }

    /**
     * 加载更多
     */
    public void getMoreData() {
        mCurrentPage = mCurrentPage + 1;
        WanService.getHomeData(mCurrentPage)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
        .subscribe(new RxObserver<ArticleListVO>() {
            @Override
            public void _onNext(ArticleListVO vo) {
                getView().getMoreDataSuccess(vo.getDatas());
            }

            @Override
            public void _onError(String errorMessage) {
                getView().getDataError(errorMessage);
            }
        });
    }

}
