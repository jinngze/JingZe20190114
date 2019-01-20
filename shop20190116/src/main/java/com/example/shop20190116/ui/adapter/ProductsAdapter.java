package com.example.shop20190116.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shop20190116.R;
import com.example.shop20190116.data.bean.ShopBean;
import com.example.shop20190116.ui.activity.CustomCounterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.BaseViewHolder> {



    private Context mContext;
    private List<ShopBean.DataBean.ListBean> mList = new ArrayList<>();

    public ProductsAdapter(Context context, List<ShopBean.DataBean.ListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = View.inflate(mContext, R.layout.shop_goods, null);
        BaseViewHolder baseViewHolder = new BaseViewHolder(view);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, final int i) {

        String url = mList.get(i).getImages().split("\\|")[0].replace("https","http");
        Glide.with(mContext).load(url).into(baseViewHolder.ivProduct);

        baseViewHolder.tvProductPrice.setText(mList.get(i).getPrice()+"");
        baseViewHolder.tvProductTitle.setText(mList.get(i).getTitle());

        //根据我记录的状态，改变勾选
        baseViewHolder.checkProduct.setChecked(mList.get(i).isCheck());

        //商品的跟商家的有所不同，商品添加了选中改变的监听
        baseViewHolder.checkProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //优先改变自己的状态
                mList.get(i).setCheck(b);
                //回调，目的是告诉activity，有人选中状态被改变
                if(mShopCallBackListener != null){
                    mShopCallBackListener.callBack();
                }
            }

        });

        //设置自定义View里的Edit
        baseViewHolder.customProductCounter.setData(this,mList,i);
        baseViewHolder.customProductCounter.setOnCallBack(new CustomCounterView.CallBackListener() {
            @Override
            public void callBack() {

                if(mShopCallBackListener != null){
                    mShopCallBackListener.callBack();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    class BaseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.check_product)
        CheckBox checkProduct;
        @BindView(R.id.iv_product)
        ImageView ivProduct;
        @BindView(R.id.tv_product_title)
        TextView tvProductTitle;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.custom_product_counter)
        CustomCounterView customProductCounter;


        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    /**
     * 在我们子商品的adapter中，修改子商品的全选和反选
     *
     * @param isSelectAll
     */

    public void selectOrRemoveAll(boolean checked) {

      for (ShopBean.DataBean.ListBean listBean : mList){

          listBean.setCheck(checked);
      }

      notifyDataSetChanged();
    }


    public  ShopCallBackListener mShopCallBackListener;


    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack();
    }

}
