package com.linky.movieapp.api.rxsubscriber;

import android.util.Log;

import com.linky.movieapp.exception.ApiException;
import com.linky.movieapp.utils.L;

import rx.Subscriber;


/**
 * Created By Linky On 2017-06-01 2:06 PM
 * *
 */
abstract class ErrorSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        // no-op
    }

    @Override
    public final void onError(Throwable e) {

        L.i("ErrorSubscriber : onError " + Log.getStackTraceString(e));

        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, Integer.MAX_VALUE));
        }
    }

    @Override
    public void onNext(T t) {
        // no-op
    }

    protected abstract void onError(ApiException e);
}
