package com.example.dian2.di.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.dian2.R;
import com.example.dian2.data.Bean.ShopBean;
import com.example.dian2.ui.adapter.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 加减数量
 */

public class ConView extends RelativeLayout implements View.OnClickListener {

   private EditText editCar;

    public ConView(Context context) {
        super(context);
        init(context);
    }

    public ConView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ConView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context,R.layout.shop_price,null);
        ImageView addImage = view.findViewById(R.id.add_car);
        ImageView jianImage = view.findViewById(R.id.jian_car);
        editCar = view.findViewById(R.id.edit_shop_car);
        addImage.setOnClickListener(this);
        jianImage.setOnClickListener(this);
        addView(view);

        editCar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //TODO:改变数量
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private int num;


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_car:
                //改变数量，设置数量，改变对象内容，回调，局部刷新
                num++;
                editCar.setText(num+"");
                list.get(position).setNum(num);
                listener.callBack();
               productsAdapter.notifyItemChanged(position);
            break;
            case R.id.jian_car:
                if(num > 1) {
                    num--;
                }else{
                    toast("我是有底线的啊");
                }
                editCar.setText(num + "");
                list.get(position).setNum(num);
                listener.callBack();
                productsAdapter.notifyItemChanged(position);
                break;
                default:
                    break;

        }
    }

    private void toast(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    //传递的数据
      private List<ShopBean.DataBean.ListBean> list  =new ArrayList<>();
      private  int position;
      private ProductsAdapter productsAdapter;


    public void setData(ProductsAdapter productsAdapter, List<ShopBean.DataBean.ListBean> list, int i) {
        this.productsAdapter = productsAdapter;
        this.list = list;
        position = i;
        num = list.get(i).getNum();
        editCar.setText(num + "");
    }

    private CallBackListener listener;

    public void setOnCallBack(CallBackListener listener) {
        this.listener = listener;
    }

    public interface CallBackListener{
        void callBack();
    }

}
