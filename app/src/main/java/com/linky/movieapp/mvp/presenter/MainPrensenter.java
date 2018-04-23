package com.linky.movieapp.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.linky.movieapp.api.Response;
import com.linky.movieapp.api.rxsubscriber.RxSubscriber;
import com.linky.movieapp.mvp.model.MainModel;
import com.linky.movieapp.mvp.view.MainView;
import com.linky.movieapp.utils.L;

import javax.inject.Inject;

/**
 * Created by oeasy on 2017/11/13.
 */

public class MainPrensenter extends BasePresenter<MainView>{
    @Inject
    MainPrensenter(){}

    @Inject
    MainModel mainModel;

    //通知界面数据请求情况
    public void getMovie(int page){
        mainModel.getMovie(page).subscribe(new RxSubscriber<Response>() {

            @Override
            public String showMessage() {
                return "正在加载";
            }

            @Override
            public boolean showDialog() {
                return true;
            }

            @Override
            public Context activity() {
                return mActivity;
            }

            @Override
            public void onErrorOccur(String msg) {
                Log.e("onErrorOccur", msg);
            }
            @Override
            public void onNextResult(Response movieBeanResponse) {
                L.e("movieBeanResponse",movieBeanResponse.toString());
                 mBaseView.onGetMovie(movieBeanResponse.list,movieBeanResponse.code,movieBeanResponse.totalPage);
            }
        });

    }

}
