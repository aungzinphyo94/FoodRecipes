package com.azp.foodapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.azp.foodapp.R;
import com.azp.foodapp.models.Categories;
import com.azp.foodapp.models.CategoriesItem;
import com.azp.foodapp.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<CategoriesItem> categoriesItems;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CategoriesAdapter(List<CategoriesItem> categoriesItems, OnItemClickListener onItemClickListener) {
        this.categoriesItems = categoriesItems;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_categories_item, viewGroup, false);
        return new CategoriesViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder categoriesViewHolder, int position) {

        final CategoriesViewHolder holder = categoriesViewHolder;
        CategoriesItem model = categoriesItems.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawableColor());
        requestOptions.error(Utils.getRandomDrawableColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(categoriesViewHolder.imageView.getContext())
                .load(model.getStrCategoryThumb())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.strCategoryName.setText(model.getStrCategory());
    }

    @Override
    public int getItemCount() {
        Log.d("CategorySize", categoriesItems.size()+"");
        return categoriesItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView strCategoryName;

        ImageView imageView;
        ProgressBar progressBar;

        OnItemClickListener onItemClickListener;

        public CategoriesViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            strCategoryName = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.img_category);
            progressBar = itemView.findViewById(R.id.progress_load_photo);

            itemView.setOnClickListener(this);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, this.getAdapterPosition());
        }
    }
}
