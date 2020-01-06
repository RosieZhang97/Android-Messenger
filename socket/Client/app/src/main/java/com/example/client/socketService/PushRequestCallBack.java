package com.example.client.socketService;

public interface PushRequestCallBack {
    void onResponse(String var);

    void onFail(String var);
}