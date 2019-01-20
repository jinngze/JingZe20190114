package com.example.shop2.di.view;

public interface IView<T> {

    void  showResponseData(T data);
    void  showResponseFail(T data);

}
