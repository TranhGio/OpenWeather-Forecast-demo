package com.android.forecast.ui.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.android.forecast.R
import com.android.forecast.arch.extensions.animSlideInRightSlideOutRight
import com.android.forecast.arch.extensions.replaceFragment
import com.android.forecast.arch.extensions.viewBinding
import com.android.forecast.databinding.ActivityMainBinding
import com.android.forecast.ui.base.BaseActivity
import com.android.forecast.ui.main.cities.CitiesFragment
import com.android.forecast.ui.main.detail.CityDetailFragment
import com.android.forecast.ui.main.wholeday.AllDayFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModels<MainViewModel>()
    private var locationManager: LocationManager? = null
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                checkLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                checkLocation()
            }
            else -> {
                Toast.makeText(
                    this,
                    "Can't get location. Please granted in setting",
                    Toast.LENGTH_SHORT
                ).show()
                exitProcess(-1)
            }
        }
    }

    override fun afterViewCreated() {
        if (isPermissionAccessible()) {
            checkLocation()
        } else {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun openCitiesFragment(lat: Float, long: Float) {
        replaceFragment(
            R.id.main_container,
            CitiesFragment.newInstance(lat, long),
            { it.animSlideInRightSlideOutRight() },
            true
        )
    }

    internal fun openCityDetailFragment(lat: Float, long: Float) {
        replaceFragment(
            R.id.main_container,
            CityDetailFragment.newInstance(lat, long),
            { it.animSlideInRightSlideOutRight() },
            true
        )
    }

    internal fun openAllDayFragment(lat: Float, long: Float, cityName: String) {
        replaceFragment(
            R.id.main_container,
            AllDayFragment.newInstance(lat, long, cityName),
            { it.animSlideInRightSlideOutRight() },
            true
        )
    }

    private fun isPermissionAccessible(): Boolean {
        return (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }


    private fun checkLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) != true) {
            onGPS()
        } else {
            requestLocationPermission()
        }
    }

    private fun onGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.enable_gps))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            val locationGPS = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            locationGPS?.let {
                openCitiesFragment(locationGPS.latitude.toFloat(), locationGPS.longitude.toFloat())
            } ?: run {
                Toast.makeText(
                    this,
                    "Can't get location. Please granted in setting",
                    Toast.LENGTH_SHORT
                ).show()
                exitProcess(-1)
            }
        }
    }
}
