package com.example.mai.model;

import com.example.mai.callback.MyCallBack;
import com.example.mai.okHttp.ICallBack;
import com.example.mai.okHttp.OkHttpUtils;

import java.util.Map;

import okhttp3.OkHttpClient;

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
