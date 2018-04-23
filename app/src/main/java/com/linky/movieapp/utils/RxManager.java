package com.linky.movieapp.utils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Author: Created by Real_Man.Data: 2017/2/16.
 * Des:  用于管理单个presenter的RxBus的事件和RxJava相关代码的生命周期处理
 */

public class RxManager {

//    public RxBus mRxBus = RxBus.getInstance();
//    //管理 RxBus 订阅 +
//    private Map<String, Observable<?>> mObservables = new HashMap<>();
//    /*管理 Observables 和 Subscribers 订阅*/
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    /*
     * RxBus注入监听
     */
//    public <T> void on(String eventName, Action1<T> action1) {
//        Observable<T> observable = mRxBus.register(eventName);
//        mObservables.put(eventName, observable);
//        /* 订阅管理 */
//        mCompositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(action1, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        throwable.printStackTrace();
//                    }
//                }));
//    }

    /**
     * 单纯的Observables 和 Subscribers管理
     */
    public void add(Subscription m) {
        /* 订阅管理 */
        mCompositeSubscription.add(m);
    }

    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    public void clear() {
        mCompositeSubscription.unsubscribe();// 取消所有订阅
//        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()) {
//            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除 rxbus 观察
//        }
    }

    //发送RxBus
//    public void post(Object tag, Object content) {
//        mRxBus.post(tag, content);
//    }
}
