package com.example.weather.presentation

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.location.LocationTracker
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.util.Resource
import com.example.weather.domain.util.currentArea
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//Inject will inform WeatherViewModel that repository and locationTracker interfaces
// bounded to it implementation in di modules
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
):ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo(context:Context){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                val address = currentArea(location.latitude,location.longitude)
                when (val result = repository.getWeatherData(location.latitude,location.longitude)) {
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null,
                            area = address
                        )
                    }
                }
            }?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location , make sure you enable location permissions and GPS "
                )
            }
        }
    }
}