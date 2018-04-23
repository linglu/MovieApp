package com.linky.movieapp.dagger;

import android.app.Application;
import android.content.Context;

import com.linky.movieapp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by oeasy on 2017/11/13.
 */

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }


    @Provides
    @Singleton
    Context provideContext(){
        return app;
    }
}
