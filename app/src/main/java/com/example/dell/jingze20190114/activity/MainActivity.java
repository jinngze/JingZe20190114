package com.example.dell.jingze20190114.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dell.jingze20190114.R;
import com.example.dell.jingze20190114.presenter.ShowPresenter;
import com.example.dell.jingze20190114.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {


    @BindView(R.id.san)
    Button btnLogin;
    @BindView(R.id.image)
    ImageView ivIcon;
    @BindView(R.id.hao)
    TextView hao;
    @BindView(R.id.ming)
    EditText ming;
    @BindView(R.id.mi)
    TextView mi;
    @BindView(R.id.ma)
    EditText ma;
    @BindView(R.id.button)
    Button button;

    private ShowPresenter showPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showPresenter = new ShowPresenter(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ming != null && ma!=null){

                    if(ming.length() != 11){
                        Toast.makeText(MainActivity.this, "请输入一位手机号" , Toast.LENGTH_SHORT).show();
                    }else if(ma.length() != 6){
                        Toast.makeText(MainActivity.this, "请输入正确密码" , Toast.LENGTH_SHORT).show();

                    }else{
                        MyLogin();
                    }
                  }


            }
        });

        //Android6.0权限配置
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }


    private void MyLogin() {
        showPresenter.login(ming.getText().toString().trim(),ma.getText().toString().trim());
        success(this);
    }


    @Override
    public void success(Object object) {

        //成功
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }

    @Override
    public void onfailed() {

        //失败
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.san})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.san:
                //获取友盟封装的分享对象
                UMShareAPI umShareAPI = UMShareAPI.get(this);
                //切记平台切换
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, authListener);
                break;
        }
    }

    UMAuthListener authListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }


        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //uid
            //name
            //gender
            //iconurl
            String uid = data.get("uid");
            String name = data.get("name");
            String gender = data.get("gender");
            String iconurl = data.get("iconurl");
            Toast.makeText(MainActivity.this, "uid:" + uid + "……name:" + name + "……gender:" + gender + "……iconurl:" + iconurl, Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "uid:" + uid + "……name:" + name + "……gender:" + gender + "……iconurl:" + iconurl);
            Glide.with(MainActivity.this).load(iconurl).into(ivIcon);


            startActivity(new Intent(MainActivity.this, SecondActivity.class));
            finish();
        }


        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(MainActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }


        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };





    //内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        showPresenter.onDetach();
    }
}

