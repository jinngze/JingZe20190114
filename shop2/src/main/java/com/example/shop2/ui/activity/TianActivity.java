package com.example.shop2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.Toast;

import com.example.shop2.R;
import com.example.shop2.data.bean.DatilBean;
import com.example.shop2.data.bean.GoodsBean;
import com.example.shop2.di.presenter.ShowPresenter;
import com.example.shop2.di.view.IView;
import com.example.shop2.ui.adapter.GoodsAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class TianActivity extends AppCompatActivity implements IView {

    private String urlStr = "http://www.zhaoapi.cn/product/searchProducts?keywords=%E6%89%8B%E6%9C%BA";
    private String path = "http://www.zhaoapi.cn/product/addCart";
    private XRecyclerView xRecyclerView;
    private ShowPresenter showPresenter;
    private GoodsAdapter mAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tian);
        initView();
        initLinear();
    }
    //实话化view
    private void initView() {
        xRecyclerView = findViewById(R.id.xrecycleView);
       showPresenter = new ShowPresenter(this);
        mAdapter = new GoodsAdapter(this);
        xRecyclerView.setAdapter(mAdapter);
        page = 1;
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
        initData();
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        showPresenter.startRequest(urlStr,map,GoodsBean.class);
    }
    private void initLinear(){
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        //分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        xRecyclerView.addItemDecoration(itemDecoration);
    }

    //获取回传的数据
    @Override
    public void showResponseData(Object data) {

        if (data instanceof GoodsBean){
            GoodsBean goodsBean = (GoodsBean) data;
            if (page == 1){
                mAdapter.setDatas(goodsBean.getData());
            }else {
                mAdapter.addDatas(goodsBean.getData());
            }
            page ++;
            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
        }else if (data instanceof DatilBean){
            DatilBean datilBean = (DatilBean) data;
            Toast.makeText(TianActivity.this, datilBean.getMsg(),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TianActivity.this, MainActivity.class));
        }

        //实现接口
        mAdapter.setClickListener(new GoodsAdapter.ClickListener() {
            @Override
            public void OnClick(String pid) {
                addCartData(pid);
            }
        });

    }

    @Override
    public void showResponseFail(Object data) {

    }

    //加入购物车
    private void addCartData(String pid) {

        Map<String, String> map = new HashMap<>();
        map.put("uid", "24198");
        map.put("pid", pid);
        showPresenter.startRequest(path,map,DatilBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showPresenter.onDetach();
    }



}
