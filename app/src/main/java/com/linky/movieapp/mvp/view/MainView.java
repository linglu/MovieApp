package com.linky.movieapp.mvp.view;

import com.linky.movieapp.bean.MovieBean;

import java.util.List;

/**
 * Created by oeasy on 2018/1/9.
 */

public interface MainView extends BaseView{
    void onGetMovie(List<MovieBean> movieBeanList, int code, int totalPage);
}
