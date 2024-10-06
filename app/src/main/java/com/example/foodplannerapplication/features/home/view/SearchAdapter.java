package com.example.foodplannerapplication.features.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapplication.R;
import com.example.foodplannerapplication.models.Meal;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchVH> {

    List<Meal> meals;
    OnSearchClick onSearchClick;

    public SearchAdapter(List<Meal> meals, OnSearchClick onSearchClick) {
        this.meals = meals;
        this.onSearchClick = onSearchClick;
    }

    public void setList(List<Meal> meals) {
        this.meals.clear();
        this.meals.addAll(meals);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVH holder, int position) {
        Meal meal = meals.get(position);
        holder.getTvItem().setText(meal.getStrMeal());
        holder.getView().setOnClickListener(v -> onSearchClick.onSearchClick(meal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class SearchVH extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView psItem;

        public SearchVH(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            psItem = itemView.findViewById(R.id.ps_search);
        }

        public View getView() {
            return view;
        }

        public TextView getTvItem() {
            return psItem;
        }
    }

    interface OnSearchClick {
        void onSearchClick(Meal meal);
    }
}
