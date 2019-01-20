package com.example.dell.jingze20190114.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.dell.jingze20190114.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class SaoActivity extends AppCompatActivity implements QRCodeView.Delegate {

    @BindView(R.id.zxingview)
    ZXingView zxingview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sao);
        ButterKnife.bind(this);

        zxingview.setDelegate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        zxingview.startCamera();
        zxingview.startSpotAndShowRect();
        zxingview.openFlashlight();
    }

    @Override
    protected void onStop() {
        super.onStop();
        zxingview.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zxingview.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        //成功

        Log.i("dj", "result is " + result);
        //TODO:
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        //环境改变，是否变暗，变暗isDark为true

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        //打开相机失败
    }
}
