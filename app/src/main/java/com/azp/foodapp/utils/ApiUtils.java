package com.azp.foodapp.utils;

import com.azp.foodapp.api.ApiClient;
import com.azp.foodapp.api.ApiInterface;

public class ApiUtils {

    public ApiUtils() {
    }

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    public static ApiInterface getAPI() {
        return ApiClient.getApiClient(BASE_URL).create(ApiInterface.class);
    }
}
