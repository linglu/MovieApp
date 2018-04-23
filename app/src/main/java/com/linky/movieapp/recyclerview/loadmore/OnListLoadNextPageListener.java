package com.linky.movieapp.recyclerview.loadmore;

import android.view.View;

public interface OnListLoadNextPageListener {

    /**
     * 开始加载下一页
     * @param view 当前RecyclerView/ListView/GridView
     */
    void onLoadNextPage(View view, int pageSize, int index);
}