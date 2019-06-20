package com.azp.foodapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azp.foodapp.R;
import com.azp.foodapp.adapter.CategoriesAdapter;
import com.azp.foodapp.api.ApiInterface;
import com.azp.foodapp.models.Categories;
import com.azp.foodapp.models.CategoriesItem;
import com.azp.foodapp.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealCategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    //    private RecyclerView.LayoutManager layoutManager;
    private List<CategoriesItem> categoriesItems = new ArrayList<>();
    private CategoriesAdapter categoriesAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView topHeadline;

    public MealCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_meal_category, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        OnLoadingSwipeRefresh("");

        topHeadline = rootView.findViewById(R.id.headline);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Categories");
    }

    @Override
    public void onRefresh() {

    }

    public void LoadCategories(final String keyword) {
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface = ApiUtils.getAPI();

        Call<Categories> categoriesCall = apiInterface.getCategories();

        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!categoriesItems.isEmpty()) {
                        categoriesItems.clear();
                    }

                    categoriesItems = response.body().getCategories();

                    CategoriesAdapter.OnItemClickListener listener = (view, position) -> {

                    };

                    categoriesAdapter = new CategoriesAdapter(categoriesItems, listener);
                    recyclerView.setAdapter(categoriesAdapter);
                    categoriesAdapter.notifyDataSetChanged();

                    topHeadline.setVisibility(View.VISIBLE);
                    topHeadline.setText("Categories");
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

                topHeadline.setVisibility(View.VISIBLE);
                topHeadline.setText("Unable to load the meals");
            }
        });
    }

    private void OnLoadingSwipeRefresh(final String keyword) {

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadCategories(keyword);
                    }
                }
        );
    }
}
