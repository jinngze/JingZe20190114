package com.example.shop2.di.model;

import com.example.shop2.data.util.ICallBack;
import com.example.shop2.di.callback.MyCallBack;

import java.util.Map;

public interface IModel {

    void requestData(String url,Map<String,String> params,Class clazz,MyCallBack callBack);

}
