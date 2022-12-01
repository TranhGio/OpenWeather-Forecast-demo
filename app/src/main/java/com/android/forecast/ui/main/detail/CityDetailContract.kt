package com.android.forecast.ui.main.detail

import com.android.forecast.arch.extensions.FlowResult
import com.android.forecast.data.model.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface CityDetailContract {
    fun getCurrentWeatherByLocation(): Flow<FlowResult<CurrentWeather>>

    fun isCelsiusUnit(): Boolean

    fun changeTemperatureUnit(): Float

    fun getCurrentWeather(): CurrentWeather?

    fun setCityData(lat: Float, long: Float)
}
