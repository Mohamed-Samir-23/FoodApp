package com.example.foodplannerapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplannerapplication.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealDAO {
    @Query("SELECT * FROM favorites_table")
    Observable<List<Meal>> getMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(Meal meal);

    @Delete
    Completable deleteMeal(Meal meal);

    @Query("DELETE FROM favorites_table")
    Completable deleteAllRecords();
}
