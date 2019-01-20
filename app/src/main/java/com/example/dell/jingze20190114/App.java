package com.example.dell.jingze20190114;

import android.app.Application;
import android.os.Looper;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;


public class App extends Application  implements Thread.UncaughtExceptionHandler{
    @Override
    public void onCreate() {
        super.onCreate();

        UMConfigure.init(this, "5c3409d8b465f52ee8000846", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);

        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");

        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");

                     //进行捕获异常
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
            //打印日志
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        new Thread(){

            @Override
            public void run() {
                super.run();
                Looper.prepare();
                   //发生崩溃进行吐丝
                Toast.makeText(App.this, "失败" , Toast.LENGTH_SHORT).show();

                Looper.loop();
            }
        }.start();


    }
}
