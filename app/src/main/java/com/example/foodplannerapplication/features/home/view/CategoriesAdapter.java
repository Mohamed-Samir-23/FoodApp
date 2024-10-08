package com.example.foodplannerapplication.features.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapplication.R;
import com.example.foodplannerapplication.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryVH> {

    List<Category> categories;
    OnCategoryClick onCategoryClick;

    public CategoriesAdapter(List<Category> categories, OnCategoryClick onCategoryClick) {
        this.categories = categories;
        this.onCategoryClick = onCategoryClick;
    }

    public void setList(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    public ArrayList<Category> getList() {
        return new ArrayList<>(categories);
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new CategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        Category category = categories.get(position);
        holder.getTvItem().setText(category.getStrCategory());
        Glide.with(holder.getView().getContext()).load(category.getStrCategoryThumb()).into(holder.getIvItem());
        holder.getView().setOnClickListener(v -> onCategoryClick.onClick(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryVH extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView ivItem;
        private final TextView psItem;

        public CategoryVH(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ivItem = itemView.findViewById(R.id.mpsitem);
            psItem = itemView.findViewById(R.id.ps_item);
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
    }

    interface OnCategoryClick {
        void onClick(Category category);
    }
}
