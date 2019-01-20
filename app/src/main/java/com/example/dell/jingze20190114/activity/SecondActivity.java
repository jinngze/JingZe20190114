package com.example.dell.jingze20190114.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.jingze20190114.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.look)
    Button look;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.success)
    Button success;
    @BindView(R.id.image_qrcode)
    ImageView imageQrcode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        findViewById(R.id.look).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        findViewById(R.id.success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQRCode();
            }
        });
    }

    //判断系统版本是否为6.0以上
    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            } else {
                startActivity(new Intent(SecondActivity.this, SaoActivity.class));
            }
        } else {
            startActivity(new Intent(SecondActivity.this, SaoActivity.class));
        }
    }

    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //如果requestCode匹配，切权限申请通过
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(SecondActivity.this, SaoActivity.class));
        }
    }

    private void createQRCode() {
        QRTask qrTask = new QRTask(this, imageQrcode, edit);
        qrTask.execute(edit.getText().toString());
    }


    static class QRTask extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<Context> mContext;
        private WeakReference<ImageView> mImageView;

        public QRTask(Context context, ImageView image, EditText ed) {
            mContext = new WeakReference<>(context);
            mImageView = new WeakReference<>(image);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String str = params[0];
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            int size = mContext.get().getResources().getDimensionPixelOffset(R.dimen.success_size);
            return QRCodeEncoder.syncEncodeQRCode(str, size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                mImageView.get().setImageBitmap(bitmap);
            } else {
                Toast.makeText(mContext.get(), "生成失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
