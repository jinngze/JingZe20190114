package com.example.dell.jingze20190114.presenter;

import com.example.dell.jingze20190114.model.IModel;
import com.example.dell.jingze20190114.model.ShowModel;
import com.example.dell.jingze20190114.view.IView;

import java.io.PrintWriter;

public class ShowPresenter {

    public ShowModel model;
    public IView iView;

    public ShowPresenter(IView iView) {
        this.iView = iView;
        this.model = new ShowModel();
    }

    public void login(String username,String password) {

        //空判断 合法性
        model.login(username, password, new IModel() {
            @Override
            public void success(Object object) {

                iView.success(object);
            }

            @Override
            public void onfailed() {

                iView.onfailed();
            }
        });

    }

    public  void onDetach(){

        if(model != null){

            model = null;
        }

        if(iView != null){

            iView = null;
        }

    }


}
