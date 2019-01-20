package com.example.dian2.di.callback;

public interface MyCallBack<T> {

    void success(T data);
    void failed(Exception e);
}
