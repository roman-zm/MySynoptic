package com.example.mysynoptic;

import com.example.mysynoptic.domain.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiService {
    @GET("weather")
    Call<Weather> getCurrentWeather(
            @Query("lat") double latitude,
            @Query("lon") double longitude,

            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
