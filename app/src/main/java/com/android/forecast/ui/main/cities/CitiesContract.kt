package com.android.forecast.ui.main.cities

import com.android.forecast.arch.extensions.FlowResult
import com.android.forecast.data.model.CityForecast
import kotlinx.coroutines.flow.Flow

interface CitiesContract {
    fun getCities(): MutableList<CityForecast>

    fun fetchCities(keyword: String): Flow<FlowResult<List<CityForecast>>>
}
