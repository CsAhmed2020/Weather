package com.example.weather.data.remote

import com.squareup.moshi.Json

//https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m,weathercode,pressure_msl,windspeed_10m,relativehumidity_2m
data class WeatherDataDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name = "weathercode")
    val weatherCodes: List<Int>,
    @field:Json(name = "pressure_msl")
    val pressures: List<Double>,
    @field:Json(name = "windspeed_10m")
    val windSpeeds: List<Double>,
    @field:Json(name = "relativehumidity_2m")
    val humidities: List<Double>
)
