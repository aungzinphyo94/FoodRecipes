package com.azp.foodapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.azp.foodapp.R;
import com.azp.foodapp.models.MealsItem;
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


public class LatestMealsAdapter extends RecyclerView.Adapter<LatestMealsAdapter.LatestMealsViewHolder> {

    private List<MealsItem> mealsItems;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public LatestMealsAdapter(List<MealsItem> mealsItems, OnItemClickListener listener) {
        this.mealsItems = mealsItems;
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public LatestMealsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_menus_item, viewGroup, false);
        return new LatestMealsViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestMealsViewHolder latestMealsViewHolder, int position) {

        final LatestMealsViewHolder holder = latestMealsViewHolder;
        MealsItem model = mealsItems.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawableColor());
        requestOptions.error(Utils.getRandomDrawableColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(latestMealsViewHolder.imageView.getContext())
                .load(model.getStrMealThumb())
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

        holder.strMeal.setText(model.getStrMeal());
        holder.strCategory.setText(model.getStrCategory());
        holder.strArea.setText(model.getStrArea());

    }

    @Override
    public int getItemCount() {
        return mealsItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class LatestMealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView strMeal, strCategory, strArea;

        ImageView imageView;
        ProgressBar progressBar;

        OnItemClickListener onItemClickListener;

        public LatestMealsViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            strMeal = itemView.findViewById(R.id.title);
            strArea = itemView.findViewById(R.id.area);
            strCategory = itemView.findViewById(R.id.category);

            imageView = itemView.findViewById(R.id.img);
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
