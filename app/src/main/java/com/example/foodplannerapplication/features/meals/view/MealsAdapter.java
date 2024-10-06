package com.example.foodplannerapplication.features.meals.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapplication.R;
import com.example.foodplannerapplication.models.Meal;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealVH> {

    List<Meal> meals;
    OnMealClick mealClick;

    boolean isDoneWork;

    public MealsAdapter(List<Meal> meals, OnMealClick mealClick,boolean isDoneWork) {
        this.meals = meals;
        this.mealClick = mealClick;
        this.isDoneWork = isDoneWork;
    }
    public void updatebackFlag(boolean isDoneWork){
        this.isDoneWork = isDoneWork;
        notifyDataSetChanged();
    }

    public void updateList(List<Meal> meals,boolean isDoneWork) {
        this.isDoneWork = isDoneWork;
        this.meals.clear();
        this.meals.addAll(meals);
        notifyDataSetChanged();
    }

    public void updateFavorites(List<Meal> favMeals){
        for (Meal meal:meals) {
            for (Meal fav:favMeals) {
                if (meal.getIdMeal().equals(fav.getIdMeal())){
                    meal.setSelected(true);
                    break;
                }else {
                    meal.setSelected(false);
                }
                meal.setSelected(fav.getIdMeal().equals(meal.getIdMeal()));
            }

        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MealVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealVH holder, int position) {
        Meal meal = meals.get(position);
        holder.getTvItem().setText(meal.getStrMeal());
        Glide.with(holder.getView().getContext()).load(meal.getStrMealThumb()).into(holder.getIvItem());

        if (isDoneWork){
            holder.getIvFav().setVisibility(View.VISIBLE);
        }else {
            holder.getIvFav().setVisibility(View.INVISIBLE);
        }

        holder.getView().setOnClickListener(v -> mealClick.onClick(meal));
        if (meal.isSelected()){
            holder.getIvFav().setColorFilter(ContextCompat.getColor(holder.getView().getContext(), R.color.red));
        }else {
            holder.getIvFav().setColorFilter(ContextCompat.getColor(holder.getView().getContext(), R.color.white));
        }
        holder.getIvFav().setOnClickListener(v->{
            if (meal.isSelected()){
                mealClick.onFavClick(meal,false);
                meal.setSelected(false);
                holder.getIvFav().setColorFilter(ContextCompat.getColor(holder.getView().getContext(), R.color.white));
            }else {
                mealClick.onFavClick(meal,true);
                meal.setSelected(true);
                holder.getIvFav().setColorFilter(ContextCompat.getColor(holder.getView().getContext(), R.color.red));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealVH extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView ivItem,ivFav;
        private final TextView psItem;

        public MealVH(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ivItem = itemView.findViewById(R.id.mpsmeal);
            psItem = itemView.findViewById(R.id.ps_meal);
            ivFav = itemView.findViewById(R.id.mpsfav);
        }

        public View getView() {
            return view;
        }

        public ImageView getIvItem() {
            return ivItem;
        }

        public TextView getTvItem() {
            return psItem;
        }

        public ImageView getIvFav() {
            return ivFav;
        }
    }

    interface OnMealClick {
        void onClick(Meal meal);

        void onFavClick(Meal meal,Boolean isSelect);
    }
}
