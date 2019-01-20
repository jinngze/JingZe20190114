package com.example.mai.presenter;

import com.example.mai.activity.IView;
import com.example.mai.callback.MyCallBack;
import com.example.mai.model.IModel;
import com.example.mai.model.ShowModel;

import java.util.Map;

public class ShowPresenter implements IPresenter {

    private IView iView;
    private IModel iModel;

    public ShowPresenter(IView iView) {
        this.iView = iView;
        iModel = new ShowModel();
    }

    @Override
    public void startRequest(String url, Map<String, String> params, Class clazz) {

        iModel.requestData(url, params, clazz, new MyCallBack() {
            @Override
            public void success(Object data) {

                iView.showResponseData(data);
            }

            @Override
            public void failed(Exception e) {

                iView.showResponseFail(e);
            }
        });

    }


    public void onDetach(){

        if(iModel != null){

            iModel = null;
        }
        if(iView != null){

            iView = null;
        }

    }

}
