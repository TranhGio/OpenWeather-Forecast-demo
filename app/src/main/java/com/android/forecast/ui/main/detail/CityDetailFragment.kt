package com.android.forecast.ui.main.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.forecast.R
import com.android.forecast.arch.extensions.onClick
import com.android.forecast.arch.extensions.onError
import com.android.forecast.arch.extensions.onSuccess
import com.android.forecast.arch.extensions.viewBinding
import com.android.forecast.databinding.FragmentCityDetailBinding
import com.android.forecast.ui.base.BaseFragment
import com.android.forecast.ui.base.BaseViewModel
import com.android.forecast.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn

@AndroidEntryPoint
class CityDetailFragment : BaseFragment(R.layout.fragment_city_detail) {

    companion object {
        private const val KEY_LAT = "CITY_LAT"
        private const val KEY_LONG = "CITY_LONG"
        fun newInstance(lat: Float, long: Float) = CityDetailFragment().apply {
            arguments = bundleOf(
                KEY_LAT to lat,
                KEY_LONG to long
            )
        }
    }

    private val viewModel by viewModels<CityDetailViewModel>()
    private val binding by viewBinding(FragmentCityDetailBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListeners()
    }

    private fun initListeners() {
        binding.run {
            textChangeUnit.onClick {
                textTemperature.text = viewModel.changeTemperatureUnit().toString()
                textTemperatureUnit.text = if (viewModel.isCelsiusUnit()) "°C" else "°F"
//                if (viewModel.isCelsiusUnit()) getString(R.string.temperature_celsius)
//                else getString(R.string.temperature_fahrenheit)
            }
            textClickToSeeWholeDayForecast.onClick {
                val currentWeather = viewModel.getCurrentWeather()
                (activity as? MainActivity)?.openAllDayFragment(
                    currentWeather?.coord?.lat ?: 0f,
                    currentWeather?.coord?.lon ?: 0f,
                    currentWeather?.name ?: ""
                )
            }
        }
    }

    private fun initData() {
        arguments?.run {
            viewModel.setCityData(getFloat(KEY_LAT, 0f), getFloat(KEY_LONG, 0f))
        }
        viewModel.getCurrentWeatherByLocation()
            .onSuccess { bindWeatherData() }
            .onError(normalAction = ::handleApiError)
            .launchIn(lifecycleScope)
    }

    private fun bindWeatherData() {
        val currentWeather = viewModel.getCurrentWeather()
        binding.run {
            textCityName.text = currentWeather?.name
            textTemperature.text = currentWeather?.main?.temp.toString()
            textTemperatureUnit.text = getString(R.string.temperature_celsius)
            textHumidity.text =
                context?.getString(R.string.humidity, currentWeather?.main?.humidity.toString())
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
