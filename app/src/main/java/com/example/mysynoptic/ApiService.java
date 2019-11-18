package com.example.mysynoptic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiService {
    @GET("weather")
    Call<WeatherPJ> getCurrentWeather(
            @Query("lat") double latitude,
            @Query("lon") double longitude,

            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
