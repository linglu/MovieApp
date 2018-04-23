package com.linky.movieapp.api.converter;

import com.alibaba.fastjson.JSON;
import com.linky.movieapp.utils.L;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * Created by oeasy on 2018/1/8.
 */

public class FastjsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {

    public Type type;

    public FastjsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String response = value.string();//value.toString();
        L.json(response);
        return JSON.parseObject(response, type);
    }
}
