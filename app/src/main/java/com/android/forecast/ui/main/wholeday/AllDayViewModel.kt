package com.android.forecast.ui.main.wholeday

import androidx.lifecycle.bindCommonError
import com.android.forecast.arch.extensions.FlowResult
import com.android.forecast.arch.extensions.onSuccess
import com.android.forecast.data.model.ForecastWeather
import com.android.forecast.data.model.Hourly
import com.android.forecast.data.repositories.ForeCastRepository
import com.android.forecast.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AllDayViewModel @Inject constructor(private val foreCastRepository: ForeCastRepository) :
    BaseViewModel(), AllDayContract {

    private var hours = mutableListOf<Hourly>()
    private var lat: Float = 0f
    private var long: Float = 0f
    private var cityName: String = ""

    override fun setAllDayData(lat: Float, long: Float, cityName: String) {
        this.lat = lat
        this.long = long
        this.cityName = cityName
    }

    override fun getCityName() = cityName

    override fun getHoursInformation() = hours

    override fun fetchAllDayWeather(): Flow<FlowResult<ForecastWeather>> {
        return foreCastRepository.getAllDayWeather(lat, long).onSuccess {
            hours.clear()
            hours.addAll(it.hourly ?: mutableListOf())
        }.bindCommonError(this)
    }
}
