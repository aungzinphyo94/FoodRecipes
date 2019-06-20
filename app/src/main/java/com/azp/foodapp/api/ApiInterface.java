package com.azp.foodapp.api;

import com.azp.foodapp.models.Categories;
import com.azp.foodapp.models.LatestResponse;
import com.azp.foodapp.models.MealsItem;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("latest.php")
    Call<LatestResponse> getLatestMeals();

    @GET("categories.php")
    Call<Categories> getCategories();

}
