package com.example.foodplannerapplication.features.plan.presenter;

import com.example.foodplannerapplication.features.plan.view.DayView;
import com.example.foodplannerapplication.features.plan.view.PlanView;
import com.example.foodplannerapplication.models.PlannedMeal;
import com.example.foodplannerapplication.models.repository.Repo;

public class PlanPresenter {
    DayView view;
    PlanView planView;
    Repo repo;

    public PlanPresenter(PlanView view, Repo repo) {
        this.planView = view;
        this.repo = repo;
    }

    public PlanPresenter(DayView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getPlannedMeals(int day) {
        view.onGetPlannedMeals(repo.getPlannedMeals(day));
    }

    public void deletePlannedMeal(PlannedMeal meal) {
        view.onPlannedMealDeleted(repo.deletePlannedMeal(meal));
    }

    public void isDoneWork(){
        planView.onGetDoneWorkFlag(repo.isDoneWork());
    }
}
