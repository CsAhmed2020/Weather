package com.example.weather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context =this
    }

    companion object {
        lateinit var context: WeatherApp
            private set
    }
}