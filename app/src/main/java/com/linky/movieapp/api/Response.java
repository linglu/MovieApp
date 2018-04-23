package com.linky.movieapp.api;

import com.linky.movieapp.bean.MovieBean;

import java.util.List;

/**
 * Created by oeasy on 2018/1/8.
 */

public class Response {
    public int code;
    public int totalPage;
    public List<MovieBean> list;
}
