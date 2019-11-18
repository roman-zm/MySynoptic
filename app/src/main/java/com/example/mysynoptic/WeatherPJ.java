package com.example.mysynoptic;

import com.google.gson.annotations.SerializedName;

public class WeatherPJ {
    private SynoMain main;
    private SynoWind wind;

    @SerializedName("name")
    private String city;

    public SynoMain getMain() {
        return main;
    }

    public SynoWind getWind() {
        return wind;
    }

    public String getCityName() {
        return city;
    }
}
