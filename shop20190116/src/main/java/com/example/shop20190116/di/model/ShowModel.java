package com.example.shop20190116.di.model;

import com.example.shop20190116.data.util.ICallBack;
import com.example.shop20190116.data.util.OkHttpUtils;
import com.example.shop20190116.di.callback.MyCallBack;

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
