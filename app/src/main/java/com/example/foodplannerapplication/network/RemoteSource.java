package com.example.foodplannerapplication.network;

public interface RemoteSource {
    <T> ApiServices makeNetworkCall(NetworkCallback<T> callback);
    <T> ApiServices callRequest();
}
