package com.example.weather.domain.util

import android.location.Address
import android.location.Geocoder
import com.example.weather.WeatherApp

fun currentArea(lat:Double,long: Double): String? {

    val geocoder = Geocoder(WeatherApp.context.applicationContext)
    val addresses: List<Address> =
        geocoder.getFromLocation(lat, long, 1)
    return addresses[0].subAdminArea
}