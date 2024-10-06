package com.example.foodplannerapplication.models.repository;

import com.example.foodplannerapplication.database.LocalSource;
import com.example.foodplannerapplication.models.Category;
import com.example.foodplannerapplication.models.Country;
import com.example.foodplannerapplication.models.Ingredient;
import com.example.foodplannerapplication.models.Meal;
import com.example.foodplannerapplication.models.PlannedMeal;
import com.example.foodplannerapplication.network.RemoteSource;


import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repo implements RepoInterface {
    private final RemoteSource remoteSource;
    private final LocalSource localSource;
    private static Repo repo = null;

    private Repo(RemoteSource remoteSource, LocalSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public static Repo getInstance(RemoteSource remoteSource, LocalSource localSource) {
        if (repo == null) {
            repo = new Repo(remoteSource, localSource);
        }
        return repo;
    }

    @Override
    public Observable<Meal> getRandomMeal() {
        return remoteSource.callRequest().getRandomMeal().subscribeOn(Schedulers.io()).map(mealsResponse -> mealsResponse.getMeals().get(0));
    }

    @Override
    public Observable<Meal> getMealById(String mealId) {
        return remoteSource.callRequest().getMealById(mealId).subscribeOn(Schedulers.io()).map(mealsResponse -> mealsResponse.getMeals().get(0));
    }

    @Override
    public Observable<List<Category>> getCategories() {
        List<Category> categories = localSource.readCategories();
        if (categories == null || categories.isEmpty()) {
            return remoteSource.callRequest().getCategories()
                    .subscribeOn(Schedulers.io())
                    .map(categoriesResponse -> {
                        localSource.storeCategories(categoriesResponse);
                        return categoriesResponse.getCategories();
                    });
        } else {
            return Observable.just(categories);
        }
    }

    @Override
    public Observable<List<Country>> getCountries() {
        List<Country> countries = localSource.readCountries();
        if (countries == null || countries.isEmpty()) {
            return remoteSource.callRequest().getCountries()
                    .subscribeOn(Schedulers.io())
                    .map(countriesResponse -> {
                        localSource.storeCountries(countriesResponse);
                        return countriesResponse.getCountries();
                    });
        } else {
            return Observable.just(countries);
        }

    }

    @Override
    public Observable<List<Ingredient>> getIngredients() {
        List<Ingredient> ingredients = localSource.readIngredients();
        if (ingredients == null || ingredients.isEmpty()) {
            return remoteSource.callRequest().getIngredients()
                    .subscribeOn(Schedulers.io())
                    .map(ingredientsResponse -> {
                        localSource.storeIngredients(ingredientsResponse);
                        return ingredientsResponse.getIngredients();
                    });
        } else {
            return Observable.just(ingredients);
        }
    }

    @Override
    public Observable<List<Meal>> getCategoryMeals(String category) {
        return remoteSource.callRequest().getCategoryMeals(category).subscribeOn(Schedulers.io())
                .map(mealsResponse -> mealsResponse.getMeals());
    }

    @Override
    public Observable<List<Meal>> getCountryMeals(String country) {
        return remoteSource.callRequest().getCountryMeals(country)
                .subscribeOn(Schedulers.io())
                .map(mealsResponse -> mealsResponse.getMeals());
    }

    @Override
    public Observable<List<Meal>> getSearchResult(String word) {
        return remoteSource.callRequest().getSearchResult(word)
                .subscribeOn(Schedulers.io())
                .map(mealsResponse -> mealsResponse.getMeals());
    }

    @Override
    public Completable deletePlannedMeals() {
        return localSource.deletePlannedMeals();
    }

    @Override
    public Completable deleteFavoriteMeals() {
        return localSource.deleteFavoriteMeals();
    }

    @Override
    public Observable<Boolean> isDoneWork() {
        return Observable.just(true)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> isFirstTime() {
        return localSource.isFirstTime();
    }

    @Override
    public void setFirstTime() {
        localSource.setFirstTime();
    }

    @Override
    public Observable<List<Meal>> getFavMeals() {
        return localSource.getFavMeals();
    }

    @Override
    public Completable insertFavMeal(Meal meal) {
        return localSource.insertFavMeal(meal);
    }

    @Override
    public Completable deleteFavMeal(Meal meal) {
        return localSource.deleteFavMeal(meal);
    }

    @Override
    public Observable<List<PlannedMeal>> getAllPlannedMeals() {
        return localSource.getAllPlannedMeals();
    }

    @Override
    public Observable<List<PlannedMeal>> getPlannedMeals(int day) {
        return localSource.getPlannedMeals(day);
    }

    @Override
    public Completable insertPlannedMeal(PlannedMeal meal) {
        return localSource.insertPlannedMeal(meal);
    }

    @Override
    public Completable deletePlannedMeal(PlannedMeal meal) {
        return localSource.deletePlannedMeal(meal);
    }


}
