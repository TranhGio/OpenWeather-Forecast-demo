package com.android.forecast.data.repositories

import com.android.forecast.arch.data.Repository
import com.android.forecast.arch.extensions.FlowResult
import com.android.forecast.arch.extensions.safeFlow
import com.android.forecast.data.OpenWeatherMapRemoteDataSource
import com.android.forecast.data.model.CityForecast
import com.android.forecast.data.model.CurrentWeather
import com.android.forecast.data.model.ForecastWeather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForeCastRepository @Inject constructor(
    private val openWeatherMapRemoteDataSource: OpenWeatherMapRemoteDataSource
) : Repository() {

     fun getCurrentWeatherCity(city: String): Flow<FlowResult<CurrentWeather>> = safeFlow {
         openWeatherMapRemoteDataSource.getCurrentWeatherCity(city)
    }

     fun getCurrentWeatherLatLng(lat: Float, long: Float): Flow<FlowResult<CurrentWeather>> = safeFlow {
         openWeatherMapRemoteDataSource.getCurrentWeatherLatLng(lat = lat, long = long)
    }

     fun getAllDayWeather(lat: Float, long: Float): Flow<FlowResult<ForecastWeather>> = safeFlow {
         openWeatherMapRemoteDataSource.getAllDayWeather(lat = lat, long = long)
    }

     fun getCityList(keyWord: String): Flow<FlowResult<List<CityForecast>>> = safeFlow {
         openWeatherMapRemoteDataSource.getCityList(keyWord = keyWord)
    }
}
