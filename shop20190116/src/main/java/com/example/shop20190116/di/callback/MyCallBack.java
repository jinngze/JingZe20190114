package com.example.shop20190116.di.callback;

public interface  MyCallBack<T> {

    void success(T data);
    void failed(Exception e);
}
