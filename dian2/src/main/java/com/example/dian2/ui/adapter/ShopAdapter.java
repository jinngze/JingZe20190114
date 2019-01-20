package com.example.dian2.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dian2.R;
import com.example.dian2.data.Bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.BaseViHolder> {


    private Context mContext;
    private List<ShopBean.DataBean> mList = new ArrayList<>();

    public ShopAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<ShopBean.DataBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = View.inflate(mContext, R.layout.shop_shop, null);
        BaseViHolder baseViHolder = new BaseViHolder(view);
        return baseViHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViHolder baseViHolder, final int i) {

        //设置商家的名字
        baseViHolder.tvShop.setText(mList.get(i).getSellerName());
        final ProductsAdapter productsAdapter = new ProductsAdapter(mContext, mList.get(i).getList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        baseViHolder.recyclerShop.setLayoutManager(linearLayoutManager);
        baseViHolder.recyclerShop.setAdapter(productsAdapter);

        baseViHolder.checkShop.setChecked(mList.get(i).isCheck());

        productsAdapter.setListener(new ProductsAdapter.ShopCallBackListener() {
            @Override
            public void callBack() {
                //从商品适配里回调回来，回给activity，activity计算价格和数量
                if(mShopCallBackListener != null){
                    mShopCallBackListener.callBack(mList);
                }

                List<ShopBean.DataBean.ListBean> listBeans = mList.get(i).getList();
                //创建一个临时的标志位，用来记录现在点击的状态
                boolean isAllChecked = true;
                for (ShopBean.DataBean.ListBean bean : listBeans){

                    if(!bean.isCheck()){
                        //只要有一个商品未选中，标志位设置成false，并且跳出循环
                        isAllChecked = false;
                        break;
                    }
                }

                //刷新商家的状态
                baseViHolder.checkShop.setChecked(isAllChecked);
                mList.get(i).setCheck(isAllChecked);

            }
        });
        //监听checkBox的点击事件
        //目的是改变旗下所有商品的选中状态
         baseViHolder.checkShop.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 mList.get(i).setCheck(baseViHolder.checkShop.isChecked());
                 //调用产品adapter的方法，用来全选和反选
                 productsAdapter.selectOrRemoveAll(baseViHolder.checkShop.isChecked());
             }
         });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class BaseViHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.check_shop)
        CheckBox checkShop;
        @BindView(R.id.tv_shop)
        TextView tvShop;
        @BindView(R.id.recycler_shop)
        RecyclerView recyclerShop;

        public BaseViHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack(List<ShopBean.DataBean> list);
    }


}
