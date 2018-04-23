package com.linky.movieapp.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.linky.movieapp.recyclerview.loadmore.EndlessRecyclerOnScrollListener;
import com.linky.movieapp.recyclerview.loadmore.LoadingFooter;
import com.linky.movieapp.recyclerview.loadmore.RecyclerViewStateUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by oeasy on 2018/1/9.
 */

public abstract class BaseActivity extends SupportActivity {

    public Context mContext;
    public Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSettings();
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        initInject();
        attachViewToPresenter();
        mContext = this;
        initView();
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initInject();
    protected abstract void attachViewToPresenter();
    protected abstract void detachViewToPresenter();




    private EndlessRecyclerOnScrollListener mOnScrollListener;

    public EndlessRecyclerOnScrollListener getOnScrollListener() {
        if (mOnScrollListener == null)
            mOnScrollListener = new EndlessRecyclerOnScrollListener(this) {
                @Override
                public void onLoadNextPage(View view, int pageNumber, int pageSize) {
                    loadNextPage(pageNumber, pageSize);
                }
            };
        return mOnScrollListener;
    }

    public void loadNextPage(int pageNumber, int pageSize) {}

    public void updateRecyclerState(RecyclerView recyclerView, int itemCount, int pageNumber, int pageSize) {

        mOnScrollListener.updateLatestItemCount(itemCount, pageNumber, pageSize);

        // 加载状态恢复正常
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal);
    }


    /**
     * 设置布局前的配置
     */
    private void beforeSettings() {
        //设置竖屏 不允许横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachViewToPresenter();
        mUnbinder.unbind();
    }

}
