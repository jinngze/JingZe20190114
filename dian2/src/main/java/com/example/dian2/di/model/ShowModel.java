package com.example.dian2.di.model;

import com.example.dian2.data.util.ICallBack;
import com.example.dian2.data.util.OkHttpUtils;
import com.example.dian2.di.callback.MyCallBack;

import java.util.Map;

public class ShowModel implements IModel {


    @Override
    public void requestData(String url, Map<String, String> params, Class clazz, final MyCallBack callBack) {

        OkHttpUtils.getInstance().postEnqueue(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object obj) {

                callBack.success(obj);
            }

            @Override
            public void failed(Exception e) {

                callBack.failed(e);
            }
        });

    }
}
