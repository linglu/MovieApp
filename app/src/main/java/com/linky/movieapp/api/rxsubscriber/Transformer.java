package com.linky.movieapp.api.rxsubscriber;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oeasy on 2018/1/8.
 */

public class Transformer {
   /* public static <T> Observable.Transformer<Response, T> transformer() {

        return responseObservable -> responseObservable.map(response -> {

            if (response.code == -1) {
                throw  new ServerException(-1,"请求失败了或者是没有数据了");
            } else {
                return response.list;
            }

        }).onErrorResumeNext(new HttpResponseFunc<>());
    }*/

    public static <T> Observable.Transformer<T, T> switchSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
