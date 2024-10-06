package com.example.foodplannerapplication.features.splash.view;

import io.reactivex.rxjava3.core.Observable;

public interface SplashView {
    void onGetDoneWorkFlag(Observable<Boolean> observable);
    void onGetFirstTimeFlag(Observable<Boolean> observable);
}
