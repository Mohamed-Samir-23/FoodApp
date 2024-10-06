package com.example.foodplannerapplication.features.favorites.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapplication.R;
import com.example.foodplannerapplication.models.Meal;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteVH> {

    private List<Meal> favorites;
    private OnFavClick onFavClick;

    public FavoritesAdapter(List<Meal> favorites, OnFavClick onFavClick) {
        this.favorites = favorites;
        this.onFavClick = onFavClick;
    }

    public void setList(List<Meal> favorites) {
        this.favorites.clear();
        this.favorites.addAll(favorites);
        notifyDataSetChanged();
    }

    public List<Meal> getFavorites() {
        return favorites;
    }

    @NonNull
    @Override
    public FavoriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, parent, false);
        return new FavoriteVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteVH holder, int position) {
        Meal meal = favorites.get(position);
        Glide.with(holder.getView().getContext()).load(meal.getStrMealThumb()).into(holder.getIvMeal());
        holder.getTvMeal().setText(meal.getStrMeal());
        holder.getTvCountry().setText(meal.getStrArea());
        holder.getTvCategory().setText(meal.getStrCategory());
        holder.getTvIngredients().setText(getIngredients(meal.getIngredients()));
        holder.getView().setOnClickListener(v -> {
            onFavClick.onClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    static class FavoriteVH extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView ivMeal;
        private final TextView psMeal, psCountry, psCategory, psIngredients;

        public FavoriteVH(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ivMeal = itemView.findViewById(R.id.mpsmeal);
            psMeal = itemView.findViewById(R.id.ps_meal);
            psCountry = itemView.findViewById(R.id.ps_country);
            psCategory = itemView.findViewById(R.id.ps_category);
            psIngredients = itemView.findViewById(R.id.ps_ingredients);
        }

        public View getView() {
            return view;
        }

        public ImageView getIvMeal() {
            return ivMeal;
        }

        public TextView getTvMeal() {
            return psMeal;
        }

        public TextView getTvCountry() {
            return psCountry;
        }

        public TextView getTvCategory() {
            return psCategory;
        }

        public TextView getTvIngredients() {
            return psIngredients;
        }
    }

    interface OnFavClick {
        void onClick(Meal meal);
    }

    private String getIngredients(List<String> ingredients) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            if (i == ingredients.size() - 1) {
                builder.append(ingredients.get(i));
            } else {
                builder.append(ingredients.get(i)).append(" - ");
            }
        }
        return builder.toString();
    }
}
