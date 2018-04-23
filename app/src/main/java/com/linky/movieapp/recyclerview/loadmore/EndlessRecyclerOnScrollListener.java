package com.linky.movieapp.recyclerview.loadmore;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.linky.movieapp.utils.L;


/**
 * Created by cundong on 2015/10/9.
 * 继承自RecyclerView.OnScrollListener，可以监听到是否滑动到页面最低部
 */
public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements OnListLoadNextPageListener {

    /**
     * 当前 RecyclerView 类型
     */
    protected LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的 item 的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;

    private Activity mActivity;

    public EndlessRecyclerOnScrollListener(Activity activity) {
        mActivity = activity;
    }

    private boolean mIsScrollUp;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mIsScrollUp = dy > 0;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        // 初始化类型信息
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        // 根据类型信息，获取最后可见的 item 的位置
        switch (layoutManagerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        // 得到当前状态 idle、settling、dragging
        currentScrollState = newState;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        /*
         * 获取可见 item 数量
         */
        int visibleItemCount = layoutManager.getChildCount();

        /*
         * 获取全部的 item 数量
         */
        int totalItemCount = layoutManager.getItemCount();

        /*
         * 有可见 item，而且 状态为 idle，而且 最后一个可见 item 为 最后一个 item
         * 此时加载下一个
         */
        if ((visibleItemCount > 0
                && currentScrollState == RecyclerView.SCROLL_STATE_IDLE
                && (lastVisibleItemPosition) >= totalItemCount - 1
                && mIsScrollUp
        )) {
            loadNextPage(recyclerView);
        }
    }

    /**
     * 取数组中最大值
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    /** 最近请求到的一页数据列表大小，用于判断所有数据是否加载完成 */
    private int mLatestItemCount = 1;   //

    private int mPageNumber = 0;        // 页码数
    private int mPageSize = 0;

    public void updateLatestItemCount(int itemCount, int pageNumber, int pageSize) {
        this.mLatestItemCount = itemCount;
        mPageNumber = pageNumber + 1;
        mPageSize = pageSize;
    }

    private void loadNextPage(final RecyclerView recyclerView) {

        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);

        if(state == LoadingFooter.State.Loading) {
            L.d("EndlessRecyclerOnScrollListener:onLoadNextPage " + "the state is Loading, just wait...");
            return;
        }

        if (mLatestItemCount < mPageSize) {
            // the end
            RecyclerViewStateUtils.setFooterViewState(
                    mActivity, recyclerView, mPageSize,
                    LoadingFooter.State.TheEnd, null);
        } else {

            // loading more
            RecyclerViewStateUtils.setFooterViewState(
                    mActivity, recyclerView, mPageSize,
                    LoadingFooter.State.Loading, null);

            onLoadNextPage(recyclerView, mPageNumber, mPageSize);     // 加载下一页数据
        }
    }

    @Override
    public void onLoadNextPage(View view, int pageNum, int pageSize) {

    }

    public enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }
}
