package com.android.forecast.ui.main.wholeday

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.SimpleItemAnimator
import com.android.forecast.R
import com.android.forecast.arch.extensions.onError
import com.android.forecast.arch.extensions.onSuccess
import com.android.forecast.arch.extensions.viewBinding
import com.android.forecast.databinding.FragmentAllDayBinding
import com.android.forecast.ui.base.BaseFragment
import com.android.forecast.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn

@AndroidEntryPoint
class AllDayFragment : BaseFragment(R.layout.fragment_all_day) {

    companion object {
        private const val KEY_LAT = "CITY_LAT"
        private const val KEY_LONG = "CITY_LONG"
        private const val KEY_CITY_NAME = "KEY_CITY_NAME"
        fun newInstance(lat: Float, long: Float, cityName: String) = AllDayFragment().apply {
            arguments = bundleOf(
                KEY_LAT to lat,
                KEY_LONG to long,
                KEY_CITY_NAME to cityName
            )
        }
    }

    private val viewModel by viewModels<AllDayViewModel>()
    private val binding by viewBinding(FragmentAllDayBinding::bind)
    private val allDayAdapter by lazy {
        AllDayAdapter(viewModel.getHoursInformation()).apply {
            onItemClicked = {
                Toast.makeText(
                    this@AllDayFragment.context,
                    "Still in-progress...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewAllDay.run {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            adapter = allDayAdapter
        }
        initData()
    }

    private fun initData() {
        arguments?.let {
            viewModel.setAllDayData(
                it.getFloat(KEY_LAT),
                it.getFloat(KEY_LONG),
                it.getString(KEY_CITY_NAME, "")
            )
        }
        viewModel.fetchAllDayWeather().onSuccess {
            allDayAdapter.notifyDataSetChanged()
            binding.textAllDayTitle.text = getString(R.string.all_day_title, viewModel.getCityName())
        }.onError(normalAction = this@AllDayFragment::handleApiError)
            .launchIn(lifecycleScope)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
