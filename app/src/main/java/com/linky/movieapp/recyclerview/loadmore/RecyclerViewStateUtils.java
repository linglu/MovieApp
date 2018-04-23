package com.linky.movieapp.recyclerview.loadmore;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cundong on 2015/11/9.
 * <p>
 * 分页展示数据时，RecyclerView的FooterView State 操作工具类
 * <p>
 * RecyclerView一共有几种State：Normal/Loading/Error/TheEnd
 */
public class RecyclerViewStateUtils {

    /**
     * 设置 headerAndFooterAdapter 的 FooterView State
     *
     * @param activity      context
     * @param recyclerView  recyclerView
     * @param pageSize      分页展示时，recyclerView每一页的数量
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     */
    public static void setFooterViewState(Activity activity,
                                          RecyclerView recyclerView,
                                          int pageSize,
                                          LoadingFooter.State state,
                                          View.OnClickListener errorListener) {

        // 如果 Activity 为 null 或者已经 finish，则直接退出
        if (activity == null || activity.isFinishing()) {
            return;
        }

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        // 如果 adapter 为 null 或者 不是 HAFRAdapter
        if (outerAdapter == null || !(outerAdapter instanceof LoadMoreRecyclerViewAdapter)) {
            return;
        }

        /*
         * 获取 HAFRAdapter
         */
        LoadMoreRecyclerViewAdapter headerAndFooterAdapter = (LoadMoreRecyclerViewAdapter) outerAdapter;

        /*
         * 只有一页的时候，就别加什么 FooterView 了
         */
        if (headerAndFooterAdapter.getInnerAdapter().getItemCount() < pageSize) {
            return;
        }

        LoadingFooter footerView;

        // 已经有 footerView 了
        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            footerView = (LoadingFooter) headerAndFooterAdapter.getFooterView();
            footerView.setState(state);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

            recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        } else {
            footerView = new LoadingFooter(activity);
            footerView.setState(state);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

            headerAndFooterAdapter.addFooterView(footerView);
            recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        }
    }

    /**
     * 获取当前RecyclerView.FooterView的状态
     */
    public static LoadingFooter.State getFooterViewState(RecyclerView recyclerView) {

        // 先获得 adapter
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        // 如果 Adapter 是 HAFR Adapter，
        if (outerAdapter != null && outerAdapter instanceof LoadMoreRecyclerViewAdapter) {

            /*
             * 如果 footer view 的数量大于 0
             */
            if (((LoadMoreRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                // 获得 FooterView
                LoadingFooter footerView = (LoadingFooter) ((LoadMoreRecyclerViewAdapter) outerAdapter).getFooterView();

                // 返回 FooterView 的状态
                return footerView.getState();
            }
        }

        return LoadingFooter.State.Normal;
    }

    /**
     * 设置当前RecyclerView.FooterView的状态
     */
    public static void setFooterViewState(RecyclerView recyclerView, LoadingFooter.State state) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof LoadMoreRecyclerViewAdapter) {
            if (((LoadMoreRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                LoadingFooter footerView = (LoadingFooter) ((LoadMoreRecyclerViewAdapter) outerAdapter).getFooterView();
                footerView.setState(state);
            }
        }
    }
}
