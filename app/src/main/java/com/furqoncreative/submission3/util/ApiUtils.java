package com.furqoncreative.submission3.util;

import com.furqoncreative.submission3.network.ApiClient;
import com.furqoncreative.submission3.network.ApiInterface;

public class ApiUtils {

    public static final String API_KEY = "e54d3aed27284a12d12c0a33ed79f7d9";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static ApiInterface getApi() {
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
