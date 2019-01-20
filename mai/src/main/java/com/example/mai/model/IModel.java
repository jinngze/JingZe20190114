package com.example.mai.model;

import com.example.mai.callback.MyCallBack;

import java.util.Map;

public interface IModel {

    void  requestData(String url, Map<String,String> params, Class clazz, MyCallBack callBack);
}
