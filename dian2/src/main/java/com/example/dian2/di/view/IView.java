package com.example.dian2.di.view;

public interface IView<T> {

    void showResponseData(T data);
    void showResposneFail(T data);
}
