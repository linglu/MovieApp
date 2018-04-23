package com.linky.movieapp.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by oeasy on 2018/1/8.
 */

public interface ApiService {

    @GET("getDyttByPage/{page}")
    Observable<Response> getMovie(@Path("page") int page);

}
