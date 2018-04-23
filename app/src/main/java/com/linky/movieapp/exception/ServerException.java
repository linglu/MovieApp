package com.linky.movieapp.exception;

/**
 * Created by oeasy on 2018/1/8.
 */

public class ServerException extends RuntimeException{
    public int code;
    public String message;

    public ServerException(int code, String message){
        this.code = code;
        this.message = message;
    }

}
