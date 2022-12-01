package com.android.forecast.ui.main.detail

import androidx.lifecycle.bindCommonError
import com.android.forecast.arch.extensions.FlowResult
import com.android.forecast.arch.extensions.convertCelsiusToFahrenheit
import com.android.forecast.arch.extensions.onSuccess
import com.android.forecast.data.model.CurrentWeather
import com.android.forecast.data.repositories.ForeCastRepository
import com.android.forecast.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CityDetailViewModel @Inject constructor(private val foreCastRepository: ForeCastRepository) :
    BaseViewModel(), CityDetailContract {

    private var isCelsiusUnit: Boolean = true
    private var currentWeather: CurrentWeather? = null
    private var lat: Float = 0f
    private var long: Float = 0f

    override fun getCurrentWeatherByLocation(): Flow<FlowResult<CurrentWeather>> {
        return foreCastRepository.getCurrentWeatherLatLng(lat, long)
            .onSuccess {
                isCelsiusUnit = true
                currentWeather = it
            }.bindCommonError(this)
    }

    override fun isCelsiusUnit() = isCelsiusUnit

    override fun changeTemperatureUnit(): Float {
        isCelsiusUnit = !isCelsiusUnit
        return if (isCelsiusUnit) {
            currentWeather?.main?.temp ?: 0f
        } else {
            (currentWeather?.main?.temp ?: 0f).convertCelsiusToFahrenheit()
        }
    }

    override fun getCurrentWeather() = currentWeather

    override fun setCityData(lat: Float, long: Float) {
        this.lat = lat
        this.long = long
    }
}