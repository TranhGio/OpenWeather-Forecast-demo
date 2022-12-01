package com.android.forecast.ui.main.wholeday

import com.android.forecast.arch.extensions.FlowResult
import com.android.forecast.data.model.ForecastWeather
import com.android.forecast.data.model.Hourly
import kotlinx.coroutines.flow.Flow

interface AllDayContract {

    fun setAllDayData(lat: Float, long: Float, cityName: String)

    fun getCityName(): String

    fun fetchAllDayWeather(): Flow<FlowResult<ForecastWeather>>

    fun getHoursInformation(): MutableList<Hourly>
}
