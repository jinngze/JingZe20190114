package com.example.mai.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.mai.APis;
import com.example.mai.Bean.ShopBean;
import com.example.mai.Constants;
import com.example.mai.R;
import com.example.mai.adapter.ShopAdapter;
import com.example.mai.model.IModel;
import com.example.mai.presenter.ShowPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {



 @BindView(R.id.iv_cricle)
    CheckBox ivCricle;
    @BindView(R.id.all_price)
    TextView allPrice;
    @BindView(R.id.sum_price_txt)
    TextView sumPriceTxt;
    private ShopAdapter mShopAdapter;
    private List<ShopBean.DataBean> mList = new ArrayList<>();
    private ShowPresenter showPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showPresenter = new ShowPresenter(this);

        ivCricle.setOnClickListener(this);
        initView();
        getData();
    }



    private void initView() {

      ivCricle.setOnClickListener(this);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mShopAdapter = new ShopAdapter(this);
        mRecyclerView.setAdapter(mShopAdapter);

        mShopAdapter.setListener(new ShopAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<ShopBean.DataBean> list) {
                //在这里重新遍历已经改状态后的数据，
                // 这里不能break跳出，因为还需要计算后面点击商品的价格和数目，所以必须跑完整个循环

                double totalPrice = 0;
                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for (int a = 0; a<list.size();a++){
                    //获取商家里商品
                    List<ShopBean.DataBean.ListBean> listAll = list.get(a).getList();
                    for (int i = 0; i<listAll.size(); i++){
                        totalNum = totalNum + listAll.get(i).getNum();
                        //取选中的状态
                        if(listAll.get(i).isCheck()) {
                            totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                              num = num + listAll.get(i).getNum();
                        }
                    }
                }

                if(num < totalNum){
                    //不是全部选中
                    ivCricle.setChecked(false);
                }else{
                    ivCricle.setChecked(true);
                }

                sumPriceTxt.setText("合计：" + totalPrice);
                allPrice.setText("去结算(" + num + ")");

            }
        });

    }

    private void getData() {

        Map<String,String> map = new HashMap<>();
 /*       map.put(Constants.UID_M,"71");*/
        showPresenter.startRequest(APis.URL_Q,map,ShopBean.class);
    }

    @Override
    public void showResponseData(Object data) {
        if(data instanceof ShopBean) {
            ShopBean shopBean = (ShopBean) data;
            mList = shopBean.getData();
            if(mList != null){
                mList.remove(0);
                mShopAdapter.setList(mList);
            }
        }

    }

    @Override
    public void showResponseFail(Object data) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_cricle:
                checkSeller(ivCricle.isChecked());
                mShopAdapter.notifyDataSetChanged();
                break;
                default:
        }
        
    }


    /**
     * 修改选中状态，获取价格和数量
     */
    private void checkSeller(boolean bool) {
        double totalPrice = 0;
        int num = 0;
        for (int a = 0; a < mList.size(); a++) {
            //遍历商家，改变状态
            ShopBean.DataBean dataBean = mList.get(a);
            dataBean.setCheck(bool);

            List<ShopBean.DataBean.ListBean> listAll = mList.get(a).getList();
            for (int i = 0; i < listAll.size(); i++) {
                //遍历商品，改变状态
                listAll.get(i).setCheck(bool);
                totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                num = num + listAll.get(i).getNum();
            }
        }

        if (bool) {
            sumPriceTxt.setText("合计：" + totalPrice);
            allPrice.setText("去结算(" + num + ")");
        } else {
            sumPriceTxt.setText("合计：0.00");
            allPrice.setText("去结算(0)");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        showPresenter.onDetach();
    }


  
}
