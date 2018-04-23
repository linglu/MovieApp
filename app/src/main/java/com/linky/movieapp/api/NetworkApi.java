package com.linky.movieapp.api;

import com.linky.movieapp.App;
import com.linky.movieapp.api.converter.FastjsonConverterFactory;
import com.linky.movieapp.utils.NetWorkUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by oeasy on 2018/1/8.
 */

public class NetworkApi {
    // 读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 30 * 1000;

    // 连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 30 * 1000;

    public static Retrofit retrofit;

    public static Map<String, Object> mServices = new HashMap<>();

    /************************* 缓存设置 *********************/
/*
   1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存*/


    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";


    static {
        // 开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 缓存
        File cacheFile = new File(App.sInstance.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        // 云端响应头拦截器，用来配置缓存策略
        Interceptor rewriteCacheControlInterceptor = chain -> {

            Request request = chain.request();
            if (!NetWorkUtils.isNetConnected(App.sInstance)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            okhttp3.Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(App.sInstance)) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(rewriteCacheControlInterceptor)
                .addNetworkInterceptor(rewriteCacheControlInterceptor)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .build();


        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(FastjsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://192.168.5.127:8080/sshblog/")
                .build();

        //http://localhost:8082/ssh/getDyttByPage/1
    }

    public static <T> T from(Class<T> service) {
        Object ss = mServices.get(service.getSimpleName());
        if (ss == null) {
            ss = retrofit.create(service);
            mServices.put(service.getSimpleName(), ss);
        }
        return (T) ss;
    }
}
