package com.example.shop20190116.di.model;

import com.example.shop20190116.di.callback.MyCallBack;

import java.util.Map;

public interface IModel  {

  void requestData(String url, Map<String,String>params, Class clazz, MyCallBack callBack);


}
