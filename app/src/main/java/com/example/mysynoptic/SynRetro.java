package com.example.mysynoptic;

import android.provider.SyncStateContract;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SynRetro {
    public static ApiService createApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.API_BASE_URL) // Базовый URL
                .addConverterFactory(GsonConverterFactory.create()) // Конвертер JSON
                .build();

        return retrofit.create(ApiService.class);
    }
}
