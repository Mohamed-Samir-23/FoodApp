package com.example.foodplannerapplication.models.repository;

import com.example.foodplannerapplication.models.Category;
import com.example.foodplannerapplication.models.Country;
import com.example.foodplannerapplication.models.Ingredient;
import com.example.foodplannerapplication.models.Meal;
import com.example.foodplannerapplication.models.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface RepoInterface {

    Observable<Meal> getRandomMeal();

    Observable<Meal> getMealById(String mealId);

    Observable<List<Category>> getCategories();

    Observable<List<Country>> getCountries();

    Observable<List<Ingredient>> getIngredients();

    Observable<List<Meal>> getCategoryMeals(String category);

    Observable<List<Meal>> getCountryMeals(String country);

    Observable<List<Meal>> getSearchResult(String word);

    Observable<Boolean> isDoneWork();

    Observable<List<Meal>> getFavMeals();

    Completable insertFavMeal(Meal meal);

    Completable deleteFavMeal(Meal meal);
    Observable<List<PlannedMeal>> getAllPlannedMeals();

    Observable<List<PlannedMeal>> getPlannedMeals(int day);

    Completable insertPlannedMeal(PlannedMeal meal);

    Completable deletePlannedMeal(PlannedMeal meal);

    Observable<Boolean> isFirstTime();

    Completable deletePlannedMeals();

    Completable deleteFavoriteMeals();

    void setFirstTime();

}
