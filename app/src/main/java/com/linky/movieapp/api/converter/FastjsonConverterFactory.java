package com.linky.movieapp.api.converter;


import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by oeasy on 2018/1/8.
 */

public class FastjsonConverterFactory extends Converter.Factory {
    public static FastjsonConverterFactory create(){
        return new FastjsonConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new FastjsonResponseBodyConverter<>(type);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FastjsonRequestBodyConverter<>();
    }
}
