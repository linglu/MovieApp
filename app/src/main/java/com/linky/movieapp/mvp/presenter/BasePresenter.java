package com.linky.movieapp.mvp.presenter;

import android.app.Activity;

import com.linky.movieapp.mvp.view.BaseView;
import com.linky.movieapp.utils.RxManager;

/**
 * Created by oeasy on 2018/1/9.
 */

public abstract class BasePresenter<V extends BaseView> {
    protected V mBaseView;
    protected Activity mActivity;
    protected RxManager mRxManager;

    public void attachView(V bv,Activity activity){
        this.mBaseView = bv;
        this.mActivity = activity;
        mRxManager = new RxManager();

        onStart();
    }

    public void onStart(){}

    public void detachView(){
        mBaseView = null;
        mRxManager.clear();
    }

}
