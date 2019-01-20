package com.example.mai.activity;

public interface IView<T> {

    void showResponseData(T data);
    void showResponseFail(T data);

}
