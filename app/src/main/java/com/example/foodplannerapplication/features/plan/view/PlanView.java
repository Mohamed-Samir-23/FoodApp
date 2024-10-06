package com.example.foodplannerapplication.features.plan.view;

import io.reactivex.rxjava3.core.Observable;

public interface PlanView {

    void onGetDoneWorkFlag(Observable<Boolean> observable);
}
