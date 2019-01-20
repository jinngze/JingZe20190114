package com.example.duo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CrosseTalkAdapter extends RecyclerView.Adapter {

    //定义两种常量  表示两种条目类型
    public static final int TYPE_LEFT_IMAGE = 0;
    public static final int TYPE_RIGHT_IMAGE = 1;
    private List<Bean.DataBean> list;
    private Context context;
    private OnLongItemClickListener mLongItemClickListener;

    public CrosseTalkAdapter(List<Bean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public interface OnLongItemClickListener {
        void onItemLongClick(int i);
    }

    public void setLongItemClickListeners(OnLongItemClickListener longItemClickListener) {
        this.mLongItemClickListener = longItemClickListener;
    }


    /**
     * 创建viewholder
     *
     * @param parent
     * @param viewType
     * @return
     */

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_LEFT_IMAGE:
                //条目布局
                view = LayoutInflater.from(context).inflate(R.layout.oneimage, null);
                holder = new TextViewHolder(view);
                break;
            case TYPE_RIGHT_IMAGE:
                view = LayoutInflater.from(context).inflate(R.layout.threeimage, null);
                holder = new ImageViewHolder(view);
                break;

        }
        return holder;

    }



    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
       Bean.DataBean dataBean = list.get(position);
        if (holder instanceof TextViewHolder) {
            String imgUrls = dataBean.getThumbnail_pic_s();
            String[] split = imgUrls.split("\\|");
            ((TextViewHolder) holder).title_rightlayout.setText(dataBean.getTitle());
            Glide.with(context).load(dataBean.getThumbnail_pic_s()).into(((TextViewHolder) holder).img_right);

            //长按
            ((TextViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mLongItemClickListener != null){
                        mLongItemClickListener.onItemLongClick(position);
                    }
                    return true;
                }
            });
        }
        //在里面设置各自的适配器  和显示的布局
          if (holder instanceof ImageViewHolder) {
            String imgUrls = dataBean.getThumbnail_pic_s();
            String[] split = imgUrls.split("\\|");
            String imgUrls1 = dataBean.getThumbnail_pic_s02();
            String[] split1 = imgUrls1.split("\\|");
            String imgUrls2 = dataBean.getThumbnail_pic_s03();
            String[] split2 = imgUrls2.split("\\|");
            Glide.with(context).load(split[0]).into(((ImageViewHolder) holder).img1);
            Glide.with(context).load(split1[0]).into(((ImageViewHolder) holder).img2);
            Glide.with(context).load(split2[0]).into(((ImageViewHolder) holder).img3);
             ((ImageViewHolder) holder).title_zhong.setText(dataBean.getTitle());


            //长按
            ((ImageViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mLongItemClickListener != null){
                        mLongItemClickListener.onItemLongClick(position);
                    }
                    return true;
                }
            });
        }
    }

    /**
     * 条目数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    //删除
    public void deleteItem(int poistion){
       list.remove(poistion);
        notifyItemRemoved(poistion);
    }

    /**
     * 获取不同的条目类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //&& imgUrls.isEmpty()
        if ( list.get(position).getThumbnail_pic_s02() == null  || list.get(position).getThumbnail_pic_s03() == null  ) {
            return TYPE_LEFT_IMAGE;
        } else {
            return TYPE_RIGHT_IMAGE;
        }
    }


    private class TextViewHolder extends RecyclerView.ViewHolder {
        private final TextView title_rightlayout;
        private final ImageView img_right;

        public TextViewHolder(View itemView) {
            super(itemView);
            title_rightlayout = itemView.findViewById(R.id.textview1);
            img_right = itemView.findViewById(R.id.image1);

        }

    }

    private class ImageViewHolder extends RecyclerView.ViewHolder  {
        private final TextView title_zhong;
        private final ImageView img1;
        private final ImageView img2;
        private final ImageView img3;

        public ImageViewHolder(View itemView) {
            super(itemView);
            title_zhong = itemView.findViewById(R.id.textview2);
            img1 = itemView.findViewById(R.id.image2);
            img2 = itemView.findViewById(R.id.image3);
            img3 = itemView.findViewById(R.id.image4);

        }

    }
}
