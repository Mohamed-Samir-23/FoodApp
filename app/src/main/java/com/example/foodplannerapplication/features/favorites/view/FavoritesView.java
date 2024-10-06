package com.example.foodplannerapplication.features.favorites.view;

import com.example.foodplannerapplication.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface FavoritesView {

    void onGetFavorites(Observable<List<Meal>> observable);
    void onDeleteFavorites(Completable completable);

    void onGetDoneWorkFlag(Observable<Boolean> observable);
}
