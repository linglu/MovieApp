package com.linky.movieapp.exception;

import android.net.ParseException;

import com.alibaba.fastjson.JSONException;
import com.linky.movieapp.utils.L;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by oeasy on 2018/1/9.
 */

public class ExceptionEngine {
    // 对应 HTTP 的状态码
    private static final int UNAUTHORIZED           = 401;
    private static final int FORBIDDEN              = 403;
    private static final int NOT_FOUND              = 404;
    private static final int REQUEST_TIMEOUT        = 408;
    private static final int INTERNAL_SERVER_ERROR  = 500;
    private static final int BAD_GATEWAY            = 502;
    private static final int SERVICE_UNAVAILABLE    = 503;
    private static final int GATEWAY_TIMEOUT        = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof SocketTimeoutException) {             // 请求超时
            ex = new ApiException(e, ERROR.HTTP_ERROR);
            ex.message = "请求超时，请检查网络";
            return ex;
        } else if (e instanceof HttpException) {             // HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";  // 均视为网络错误
                    break;
            }

            return ex;

        } else if (e instanceof ServerException) {    // 服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";            // 均视为解析错误
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.message = "连接失败";            // 均视为网络错误
            return ex;
        } else if (e instanceof TokenInvalidException) {    // token 失效错误
            ex = new ApiException(e, ((TokenInvalidException) e).code);
            ex.message = "token 失效，请重新登录";
            return ex;
        } else {
            L.i("ExceptionEngine:handleException " + e.getMessage());
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.message = "未知错误";            // 未知错误
            return ex;
        }
    }


    /**
     * 约定异常
     */

    private class ERROR {
        /**
         * 未知错误
         */
        static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        static final int HTTP_ERROR = 1003;
    }
}
