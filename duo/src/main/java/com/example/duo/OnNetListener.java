package com.example.duo;

public interface OnNetListener {
    void onSuccess(String result);
    void onFailed(Exception e);
}