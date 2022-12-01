package com.android.forecast.ui.main.cities

import androidx.lifecycle.bindCommonError
import com.android.forecast.arch.extensions.FlowResult
import com.android.forecast.arch.extensions.onSuccess
import com.android.forecast.data.model.CityForecast
import com.android.forecast.data.repositories.ForeCastRepository
import com.android.forecast.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val foreCastRepository: ForeCastRepository) :
    BaseViewModel(), CitiesContract {

    var lat : Float = 0f
    var long : Float = 0f

    private val cities = mutableListOf<CityForecast>()

    override fun getCities() = cities

    override fun fetchCities(keyword: String): Flow<FlowResult<List<CityForecast>>> {
        return foreCastRepository.getCityList(keyword).onSuccess {
            cities.clear()
            cities.addAll(it)
        }.bindCommonError(this)
    }
}