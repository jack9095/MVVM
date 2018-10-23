package com.example.fly.mvvm.network;


/**
 * 接口请求异常
 */
public class ServerException extends RuntimeException {
    public String message;

    public int code;

    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
