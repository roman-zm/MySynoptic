package com.example.mysynoptic.domain

import com.google.gson.annotations.SerializedName

data class Weather(
        val main: SynoMain,
        val wind: SynoWind,
        @SerializedName("name")
        val cityName: String
)