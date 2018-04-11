package com.zon.wanandroid.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zon.wanandroid.R;
import com.zon.wanandroid.api.WanService;
import com.zon.wanandroid.app.AppConst;
import com.zon.wanandroid.model.pojo.ArticleBean;
import com.zon.wanandroid.ui.activity.WebViewActivity;
import com.zon.wanandroid.util.PrefUtils;
import com.zon.wanandroid.util.T;
import com.zon.wanandroid.util.UIUtils;

import java.util.List;

/**
 * Created by Zon on 2018/3/26 0026.
 * All Rights Resered by HangZhou @2018-2019
 * 文章列表适配器:https://github.com/CymChad/BaseRecyclerViewAdapterHelpe
 */
public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {
    private Context mContext;


    public ArticleListAdapter(Context context, @Nullable List<ArticleBean> data) {
        super(R.layout.item_article, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder holder, ArticleBean bean) {
        holder.setText(R.id.tv_title, Html.fromHtml(bean.getTitle()))
                .setText(R.id.tv_author, bean.getAuthor())
                .setText(R.id.tv_time, bean.getNiceDate())
                .setText(R.id.tv_type, bean.getChapterName());
        TextView tvCollect = holder.getView(R.id.tv_collect);
        if (bean.isCollect()) {
            tvCollect.setText(UIUtils.getString(R.string.ic_collect_sel));
            tvCollect.setTextColor(UIUtils.getColor(R.color.main));
        } else {
            tvCollect.setText(UIUtils.getString(R.string.ic_collect_nor));
            tvCollect.setTextColor(UIUtils.getColor(R.color.text3));
        }
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectArticle(tvCollect, bean);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.runActivity(mContext, bean.getLink());
            }
        });
    }

    //收藏文章
    private void collectArticle(TextView tvCollect, ArticleBean bean) {
        if (PrefUtils.getBoolean(mContext, AppConst.IS_LOGIN_KEY, false) == false) {
            T.showShort(mContext, "请先登录");
            return;
        }
        if (bean.isCollect()) {
            //已经收藏，点击取消收藏
            unCollectArticler(bean, tvCollect);
        } else {
            collectArticler(bean, tvCollect);
        }
    }

    //    todo
    private void collectArticler(ArticleBean bean, TextView collect) {
    }

    private void unCollectArticler(ArticleBean bean, TextView collect) {

    }


}
