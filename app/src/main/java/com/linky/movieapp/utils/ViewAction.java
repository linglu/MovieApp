package com.linky.movieapp.utils;

import rx.functions.Action1;

/**
 * Created by linky on 9/13/17.
 * 绑定某个 View 上的点击事件及其操作
 */
public class ViewAction {

    int resId;
    Action1<Void> action;

    public ViewAction(int resId, Action1<Void> action) {
        this.resId = resId;
        this.action = action;
    }

    public static ViewAction newInstance(int resId, Action1<Void> action) {
        return new ViewAction(resId, action);
    }
}
