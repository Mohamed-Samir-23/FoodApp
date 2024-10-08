package com.example.foodplannerapplication.features.plan.view;

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
import com.example.foodplannerapplication.models.PlannedMeal;
import com.example.foodplannerapplication.util.Constants;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.PlanMealVH> {

    private List<PlannedMeal> plannedMeals;
    private OnPlannedMealClick onPlannedMealClick;

    public DayAdapter(List<PlannedMeal> plannedMeals, OnPlannedMealClick onPlannedMealClick) {
        this.plannedMeals = plannedMeals;
        this.onPlannedMealClick = onPlannedMealClick;
    }

    public void setList(List<PlannedMeal> plannedMeals) {
        this.plannedMeals.clear();
        this.plannedMeals.addAll(plannedMeals);
        notifyDataSetChanged();
    }

    public List<PlannedMeal> getPlannedMeals() {
        return plannedMeals;
    }

    @NonNull
    @Override
    public PlanMealVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item, parent, false);
        return new PlanMealVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanMealVH holder, int position) {
        PlannedMeal meal = plannedMeals.get(position);
        holder.getTvType().setText(getTypeName(meal.getType()));
        Glide.with(holder.getView().getContext()).load(meal.getMeal().getStrMealThumb()).into(holder.getIvMeal());
        holder.getTvMeal().setText(meal.getMeal().getStrMeal());
        holder.getTvCountry().setText(meal.getMeal().getStrArea());
        holder.getTvCategory().setText(meal.getMeal().getStrCategory());
        holder.getTvIngredients().setText(getIngredients(meal.getMeal().getIngredients()));
        holder.getView().setOnClickListener(v -> {
            onPlannedMealClick.onClick(meal.getMeal());
        });
        holder.getIvDelete().setOnClickListener(v->{
            onPlannedMealClick.onDeleteClick(meal,position);
        });
    }

    @Override
    public int getItemCount() {
        return plannedMeals.size();
    }

    static class PlanMealVH extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView ivMeal,ivDelete;
        private final TextView psType, psMeal, psCountry, psCategory, psIngredients;

        public PlanMealVH(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ivDelete = itemView.findViewById(R.id.mpsdelete);
            psType = itemView.findViewById(R.id.ps_type);
            ivMeal = itemView.findViewById(R.id.mpsmeal);
            psMeal = itemView.findViewById(R.id.ps_meal);
            psCountry = itemView.findViewById(R.id.ps_country);
            psCategory = itemView.findViewById(R.id.ps_category);
            psIngredients = itemView.findViewById(R.id.ps_ingredients);
        }

        public View getView() {
            return view;
        }

        public TextView getTvType() {
            return psType;
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

        public ImageView getIvDelete() {
            return ivDelete;
        }
    }

    interface OnPlannedMealClick {
        void onClick(Meal meal);
        void onDeleteClick(PlannedMeal plannedMeal,int position);
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

    private String getTypeName(int type) {
        switch (type) {
            case Constants.MealTypes.BREAKFAST:
                return "Breakfast";
            case Constants.MealTypes.LUNCH:
                return "Lunch";
            case Constants.MealTypes.DINNER:
                return "Dinner";
            default:
                return "";
        }
    }


}
