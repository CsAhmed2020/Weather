package com.example.weather.data.mappers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weather.data.remote.WeatherDataDto
import com.example.weather.data.remote.WeatherDto
import com.example.weather.domain.weather.WeatherData
import com.example.weather.domain.weather.WeatherInfo
import com.example.weather.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)
//extension function to map dto to domain level

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    // "hourly": { "time": ["2022-07-01T00:00", "2022-07-01T01:00", "2022-07-01T02:00", ...],
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        Log.d("AhmedWeatherMapper",it.toString())
        it.index / 24
    }.mapValues {
        Log.d("AhmedWeatherMapper2",it.toString())
        it.value.map {
            Log.d("AhmedWeatherMapper3",it.toString())
            it.data
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = when {
            now.minute < 30 -> now.hour
            now.hour == 23 -> 12.00
            else -> now.hour + 1
        }

        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}
