package com.example.foodplannerapplication.database;

import com.example.foodplannerapplication.models.CategoriesResponse;
import com.example.foodplannerapplication.models.Category;
import com.example.foodplannerapplication.models.CountriesResponse;
import com.example.foodplannerapplication.models.Country;
import com.example.foodplannerapplication.models.Ingredient;
import com.example.foodplannerapplication.models.IngredientsResponse;
import com.example.foodplannerapplication.models.Meal;
import com.example.foodplannerapplication.models.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface LocalSource {
    //optional till now just for testing

    Completable deletePlannedMeals();
    Completable deleteFavoriteMeals();

    Completable insertPlannedMeal(PlannedMeal meal);

    Completable deletePlannedMeal(PlannedMeal meal);

    Observable<List<PlannedMeal>> getPlannedMeals(int day);
    Observable<List<PlannedMeal>> getAllPlannedMeals();

    Completable insertFavMeal(Meal meal);

    Completable deleteFavMeal(Meal meal);

    Observable<List<Meal>> getFavMeals();

    void storeCategories(CategoriesResponse response);

    void storeCountries(CountriesResponse response);

    void storeIngredients(IngredientsResponse response);

    void setFirstTime();

    Observable<Boolean> isFirstTime();

    List<Category> readCategories();

    List<Country> readCountries();

    List<Ingredient> readIngredients();

}
