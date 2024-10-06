package com.example.foodplannerapplication.features.favorites.presenter;

import com.example.foodplannerapplication.features.favorites.view.FavoritesView;
import com.example.foodplannerapplication.models.Meal;
import com.example.foodplannerapplication.models.repository.Repo;

public class FavoritesPresenter {
    FavoritesView view;
    Repo repo;

    public FavoritesPresenter(FavoritesView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getFavorites(){
        view.onGetFavorites(repo.getFavMeals());
    }
    public void deleteFavorite(Meal meal){
        view.onDeleteFavorites(repo.deleteFavMeal(meal));
    }

    public void isDoneWork(){
        view.onGetDoneWorkFlag(repo.isDoneWork());
    }
}
