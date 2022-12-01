package com.android.forecast.ui.main.cities

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.SimpleItemAnimator
import com.android.forecast.R
import com.android.forecast.arch.extensions.*
import com.android.forecast.databinding.FragmentCitiesBinding
import com.android.forecast.ui.base.BaseActivity
import com.android.forecast.ui.base.BaseFragment
import com.android.forecast.ui.base.BaseViewModel
import com.android.forecast.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil.hideKeyboard

@AndroidEntryPoint
class CitiesFragment : BaseFragment(R.layout.fragment_cities) {

    companion object {
        private const val KEY_LAT = "CITY_LAT"
        private const val KEY_LONG = "CITY_LONG"
        fun newInstance(lat: Float, long: Float) = CitiesFragment().apply {
            arguments = bundleOf(
                KEY_LAT to lat,
                KEY_LONG to long
            )
        }
    }

    private val viewModel by viewModels<CitiesViewModel>()
    private val binding by viewBinding(FragmentCitiesBinding::bind)
    private val citiesAdapter by lazy {
        CitiesAdapter(viewModel.getCities()).apply {

            onItemClicked = { position ->
                val lat = viewModel.getCities()[position].lat ?: 0f
                val long = viewModel.getCities()[position].long ?: 0f
                (activity as MainActivity).openCityDetailFragment(lat, long)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            recyclerViewCities.run {
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
                adapter = citiesAdapter
            }
            layoutCurrentLocation.textViewCityName.text = getString(R.string.current_location)
        }
        initData()
        initListeners()
    }

    private fun initData() {
        arguments?.let {
            viewModel.lat = it.getFloat(KEY_LAT, 0f)
            viewModel.long = it.getFloat(KEY_LONG, 0f)
        }
    }

    private fun initListeners() {
        binding.run {
            inputFindCityWeather.onSearchClicked {
                if (inputFindCityWeather.text?.isNotBlank() == true) {
                    viewModel.fetchCities(inputFindCityWeather.text.toString()).onSuccess {
                        citiesAdapter.notifyDataSetChanged()
                    }.onError(normalAction = this@CitiesFragment::handleApiError)
                        .launchIn(lifecycleScope)
                }
                (activity as? BaseActivity)?.let {
                    hideKeyboard(it)
                    inputFindCityWeather.clearFocus()
                }
            }

            layoutCurrentLocation.itemCity.onClick {
                (activity as MainActivity).run {
                    openCityDetailFragment(viewModel.lat, viewModel.long)
                }
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}