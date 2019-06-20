package com.azp.foodapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.azp.foodapp.R;
import com.azp.foodapp.activities.DetailsActivity;
import com.azp.foodapp.adapter.LatestMealsAdapter;
import com.azp.foodapp.api.ApiInterface;
import com.azp.foodapp.models.LatestResponse;
import com.azp.foodapp.models.MealsItem;
import com.azp.foodapp.utils.ApiUtils;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LatestMealsFragment} interface
 * to handle interaction events.
 * Use the {@link LatestMealsFragment#} factory method to
 * create an instance of this fragment.
 */
public class LatestMealsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
//    private RecyclerView.LayoutManager layoutManager;
    private List<MealsItem> mealsItems = new ArrayList<>();
    private LatestMealsAdapter latestMealsAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView topHeadline;

    public LatestMealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_latest_meals, container, false);

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
        getActivity().setTitle("Latest Meals");
    }

    @Override
    public void onRefresh() {

    }

    public void LoadLatestMeals(final String keyword) {

        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface = ApiUtils.getAPI();

        Call<LatestResponse> latestResponseCall = apiInterface.getLatestMeals();

        latestResponseCall.enqueue(new Callback<LatestResponse>() {
            @Override
            public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {
                if (response.isSuccessful() && response.body().getMeals() != null ) {
                    if (!mealsItems.isEmpty()) {
                        mealsItems.clear();
                    }

//                    LatestMealsAdapter adapter = new LatestMealsAdapter(listener);

                    LatestMealsAdapter.OnItemClickListener listener = (view, position) -> {
                        Toast.makeText(getContext(), position +"",Toast.LENGTH_SHORT).show();
                    };

                    mealsItems = response.body().getMeals();
                    Log.d("Meals Data", mealsItems.toString());
                    latestMealsAdapter = new LatestMealsAdapter(mealsItems, listener);
                    recyclerView.setAdapter(latestMealsAdapter);
                    latestMealsAdapter.notifyDataSetChanged();

                    topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                    initListener();
                }
            }

            @Override
            public void onFailure(Call<LatestResponse> call, Throwable t) {
                topHeadline.setVisibility(View.VISIBLE);
                topHeadline.setText("Unable to load the meals");
            }
        });

    }

    private void initListener() {
        latestMealsAdapter.setOnItemClickListener(new LatestMealsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void OnLoadingSwipeRefresh(final String keyword) {

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadLatestMeals(keyword);
                    }
                }
        );
    }
}
