package com.linky.movieapp.recyclerview.movie;

import com.linky.movieapp.R;
import com.linky.movieapp.bean.MovieBean;
import com.linky.movieapp.recyclerview.MultiItemTypeSupport;

/**
 * Created by oeasy on 2018/1/10.
 */

public class MovieMulti implements MultiItemTypeSupport<MovieBean> {
    @Override
    public int getLayoutId(int itemType) {
        return R.layout.item_movie;
    }

    @Override
    public int getItemViewType(int position, MovieBean movieBean) {
        return 0;
    }
}
