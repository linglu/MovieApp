package com.linky.movieapp.exception;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oeasy on 2018/1/8.
 */

public class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
    @Override
    public Observable<T> call(Throwable throwable) {
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
