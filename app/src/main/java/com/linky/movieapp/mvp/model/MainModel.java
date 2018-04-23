package com.linky.movieapp.mvp.model;

import com.linky.movieapp.api.ApiService;
import com.linky.movieapp.api.NetworkApi;
import com.linky.movieapp.api.Response;
import com.linky.movieapp.api.rxsubscriber.Transformer;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by oeasy on 2017/11/13.
 */

public class MainModel {
    @Inject
    MainModel(){}

    /**
     * 获取电影
     */
    public Observable<Response> getMovie(int page){
        return NetworkApi.from(ApiService.class)
                .getMovie(page)
                .compose(Transformer.switchSchedulers());
    }

}
