package com.linky.movieapp;

import android.app.Application;

import com.linky.movieapp.dagger.AppComponent;
import com.linky.movieapp.dagger.AppModule;
import com.linky.movieapp.dagger.DaggerAppComponent;
import com.linky.movieapp.dagger.ProviderModule;

/**
 * Created by oeasy on 2018/1/8.
 */

public class App extends Application{
    private AppComponent appComponent;
    public static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        appComponent = createComponent();
    }

    private AppComponent createComponent(){
        return  DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .providerModule(new ProviderModule())
                .build();
    }
    public static AppComponent getInjectComponent(){
        return sInstance.appComponent;
    }
}
