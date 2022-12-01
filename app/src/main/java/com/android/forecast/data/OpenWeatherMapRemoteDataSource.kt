package com.android.forecast.data

import com.android.forecast.arch.extensions.apiCall
import com.android.forecast.data.model.CityForecast
import com.android.forecast.data.model.CurrentWeather
import com.android.forecast.data.model.ForecastWeather
import com.android.forecast.data.network.ApiService
import javax.inject.Inject

class OpenWeatherMapRemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getCurrentWeatherCity(city: String): CurrentWeather = apiCall {
        apiService.getWeatherTodayCity(city)
    }

    suspend fun getCurrentWeatherLatLng(lat: Float, long: Float): CurrentWeather = apiCall {
        apiService.getWeatherTodayLatLng(lat = lat, lon = long)
    }

    suspend fun getAllDayWeather(lat: Float, long: Float): ForecastWeather = apiCall {
        apiService.getWeatherForecast(lat = lat, lon = long)
    }

    suspend fun getCityList(keyWord: String): List<CityForecast> = apiCall {
        apiService.getCityList(keyWord = keyWord)
    }
}
