package com.example.dell.jingze20190114.model;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowModel {


    public void login(String username,String password,final IModel iModel){

        OkHttpClient okHttpClient = new OkHttpClient();

        final Request request = new Request.Builder().url("http://www.zhaoapi.cn/user/login").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iModel.onfailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                iModel.success(response.body().string());
            }
        });
    }



}
