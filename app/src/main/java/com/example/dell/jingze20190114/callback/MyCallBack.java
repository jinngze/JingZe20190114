package com.example.dell.jingze20190114.callback;

public interface MyCallBack<T> {

    void success(T data);
    void failed(Exception e);
}
