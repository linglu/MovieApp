package com.linky.movieapp.utils;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by Linky on 9/13/17.
 */
public class ViewUtils {

    /**
     * 用于处理界面点击
     *
     * @param view   点击的 view
     * @param action 点击后的响应
     */
    public static void onClick(View view, final Action1<Void> action) {

        if (CommonUtils.isNotNull(action)) {
            RxView.clicks(view)
                    .throttleFirst(2, TimeUnit.SECONDS)
                    .subscribe(action);
        }
    }

    public static void onClicks(View source, ViewAction... vab) {

        for (ViewAction aVab : vab) {
            RxView.clicks(source.findViewById(aVab.resId))
                    .throttleFirst(2, TimeUnit.SECONDS)
                    .subscribe(aVab.action);
        }
    }
}
