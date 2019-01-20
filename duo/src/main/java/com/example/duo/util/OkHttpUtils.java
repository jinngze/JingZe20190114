package com.example.duo.util;

import android.os.Handler;
import android.os.Looper;

import com.example.duo.OnNetListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static volatile OkHttpUtils mInstance;
    private OkHttpClient mClient;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 第一步，写一个单例，这里用的懒汉式，也可以使用饿汉
     * @return
     */
    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (null == mInstance) {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 完成构造方法，OkHttpClient
     */
    private OkHttpUtils() {
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /**
         * 使用构造者模式
         * 设置连接超时
         * 读取超时
         * 写超时
         * 添加拦截器
         */
        mClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    /**
     * 创建异步post方法
     * 创建表单FormBody
     * 把我们取出的key和value对应的放进去
     * 最后，build表单，生成RequestBody
     *
     * @param url 请求地址
     * @param params
     * @param clazz
     * @param callBack
     */
  public void postEnqueue(final String url, Map<String, String> params, final Class clazz, final ICallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();


        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(result, clazz);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.success(o);
                    }
                });
            }
        });
    }



    public void doGet(String url, final OnNetListener onNetListener) {
        //创建Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //发送请求
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
               mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //拿到服务器返回的数据
                final String string = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(string);
                    }
                });
            }
        });
    }



}
