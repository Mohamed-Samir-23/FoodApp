package com.example.foodplannerapplication.features.splash.presenter;

import com.example.foodplannerapplication.features.splash.view.SplashView;
import com.example.foodplannerapplication.models.repository.Repo;

public class SplashPresenter {

    SplashView view;
    Repo repo;

    public SplashPresenter(SplashView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void isDoneWork() {
        view.onGetDoneWorkFlag(repo.isDoneWork());
    }

    public void getCategories() {
        repo.getCategories().subscribe();
    }

    public void getCountries() {
        repo.getCountries().subscribe();
    }

    public void getIngredients() {
        repo.getIngredients().subscribe();
    }

    public void isFirstTime(){
        view.onGetFirstTimeFlag(repo.isFirstTime());
    }

}
