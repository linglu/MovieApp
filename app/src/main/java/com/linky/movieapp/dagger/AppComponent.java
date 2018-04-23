package com.linky.movieapp.dagger;


import com.linky.movieapp.ui.MainActivity;

import dagger.Component;

/**
 * 注解需要一一对应
 * Created by oeasy on 2017/11/13.
 */
@Component(
        modules = {
                AppModule.class,
                ProviderModule.class
        }
)
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
