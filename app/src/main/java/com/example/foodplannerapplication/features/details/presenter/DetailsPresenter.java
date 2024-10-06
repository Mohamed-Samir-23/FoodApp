package com.example.foodplannerapplication.features.details.presenter;

import com.example.foodplannerapplication.features.details.view.DetailsView;
import com.example.foodplannerapplication.models.Meal;
import com.example.foodplannerapplication.models.PlannedMeal;
import com.example.foodplannerapplication.models.repository.Repo;

public class DetailsPresenter {
    DetailsView view;
    Repo repo;

    public DetailsPresenter(DetailsView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void insertMeal(Meal meal) {
        view.onFavMealInserted(repo.insertFavMeal(meal));
    }

    public void deleteMeal(Meal meal) {
        view.onFavMealDeleted(repo.deleteFavMeal(meal));
    }

    public void getMeals() {
        view.onGetFavMeals(repo.getFavMeals());
    }

    public void insertPlannedMeal(PlannedMeal meal) {
        view.onPlannedMealInserted(repo.insertPlannedMeal(meal));
    }

    public void isDoneWork(){
        view.onGetDoneWorkFlag(repo.isDoneWork());
    }
}
