package com.linky.movieapp.api.converter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.linky.movieapp.utils.L;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by oeasy on 2018/1/8.
 */

public class FastjsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    @Override
    public RequestBody convert(T value) throws IOException {
        String body = JSON.toJSONString(value);
        L.json(body);
        Log.e("TAG", "convert: " + body);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),body);
    }
}
