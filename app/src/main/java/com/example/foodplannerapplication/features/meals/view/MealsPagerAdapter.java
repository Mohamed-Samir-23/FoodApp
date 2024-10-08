package com.example.foodplannerapplication.features.meals.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodplannerapplication.models.Category;
import com.example.foodplannerapplication.models.Country;

import java.util.ArrayList;

public class MealsPagerAdapter<T> extends FragmentStateAdapter {
    private final ArrayList<T> list;
    private TabsView tabsView;

    public MealsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<T> list, TabsView tabsView) {
        super(fragmentManager, lifecycle);
        this.list = list;
        this.tabsView = tabsView;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        T item = list.get(position);
        if (item instanceof Category) {
            return new MealsFragment<>((Category) item, tabsView);
        } else {
            return new MealsFragment<>((Country) item, tabsView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
