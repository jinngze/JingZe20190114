package com.example.dian2.di.presenter;

import java.util.Map;

public interface IPresenter {

    void startReuest(String url, Map<String,String> params, Class clazz);
}
