package com.example.duo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.duo.util.OkHttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private CrosseTalkAdapter crosseTalkAdapter ;
    private List<Bean.DataBean> listBeans = new ArrayList<>();
    private String url ="http://www.xieast.com/api/news/news.php?page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏原有标题
        //getSupportActionBar().hide();
        initView();
        crosseTalkAdapter = new CrosseTalkAdapter(listBeans, this);
        mRv.setAdapter(crosseTalkAdapter);

        crosseTalkAdapter.setLongItemClickListeners(new CrosseTalkAdapter.OnLongItemClickListener() {
            @Override
            public void onItemLongClick(int i) {
                showAlertDialog(i);
            }
        });
            OkHttpUtils.getInstance().doGet(url, new OnNetListener() {
            @Override
            public void onSuccess(String result) {
                Bean bean = new Gson().fromJson(result, Bean.class);
                List<Bean.DataBean> data = bean.getData();
           /*    MyAdapter myAdapter = new MyAdapter(MainActivity.this, data);
                mRv.setAdapter(myAdapter);*/
                     crosseTalkAdapter = new CrosseTalkAdapter(data, MainActivity.this);
                  mRv.setAdapter(crosseTalkAdapter);

            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }



    private void showAlertDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("提示：");
        builder.setMessage("请问你要删除吗?");
        builder.setIcon(R.mipmap.ic_launcher_round);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(false);
        //设置正面按钮
        builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               crosseTalkAdapter.deleteItem(i);
                crosseTalkAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", null);
        builder.show();

    }

    private void initView() {
        mRv =  findViewById(R.id.recycle);
        mRv.setLayoutManager(new LinearLayoutManager(this));
    }
}
