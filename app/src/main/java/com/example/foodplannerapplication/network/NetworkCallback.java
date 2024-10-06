package com.example.foodplannerapplication.network;


public interface NetworkCallback<T>{
    void onSuccess(T response);

    void onFailure(String errorMsg);
}
