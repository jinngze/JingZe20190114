package com.example.shop2.ui.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shop2.R;
import com.example.shop2.data.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GoodsBean.DataBean> mData;
    private Context mContext;

    public GoodsAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setDatas(List<GoodsBean.DataBean> data) {
        mData.clear();
        if (data != null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<GoodsBean.DataBean> data) {
        if (data != null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
         final ViewHolder mHolder = (ViewHolder) viewHolder;
         mHolder.text_title.setText(mData.get(i).getTitle());
         mHolder.text_price.setText("￥"+mData.get(i).getPrice()+"");
         //截取图片集
        String str = "";
        int length = mData.get(i).getImages().length();
        for (int j = 0; j < length; j++) {
            if (mData.get(i).getImages().substring(j, j+1).equals("|")){
                str = mData.get(i).getImages().substring(j+1, length).trim();
            }
        }
        Glide.with(mContext).load(str).into(mHolder.imageView);
        //接口回调加入购物车
        mHolder.add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null){
                    mClickListener.OnClick(mData.get(i).getPid()+"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView text_title, text_price;
        public Button add_car;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            text_title = itemView.findViewById(R.id.text_title);
            text_price = itemView.findViewById(R.id.text_price);
            add_car = itemView.findViewById(R.id.add_car);
        }
    }

    //成员变量
    ClickListener mClickListener;
    //set方法
    public void setClickListener(ClickListener clickListener){
        mClickListener = clickListener;
    }
    //定义个点击
    public interface ClickListener{
        void OnClick(String pid);
    }

}
