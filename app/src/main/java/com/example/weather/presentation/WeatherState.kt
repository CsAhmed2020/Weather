package com.example.weather.presentation

import com.example.weather.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo:WeatherInfo? = null,
    val isLoading : Boolean = false,
    val error: String? = null,
    val area : String? = null
)