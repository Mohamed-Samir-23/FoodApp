package com.example.foodplannerapplication.features.plan.view;

import com.example.foodplannerapplication.models.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface DayView {
    void onGetPlannedMeals(Observable<List<PlannedMeal>> observable);
    void onPlannedMealDeleted(Completable completable);
}
