package com.android.forecast.data.network

import com.android.forecast.BuildConfig
import com.android.forecast.data.model.CityForecast
import com.android.forecast.data.model.CurrentWeather
import com.android.forecast.data.model.ForecastWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather/")
    suspend fun getWeatherTodayCity(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<CurrentWeather>

    @GET("data/2.5/weather/")
    suspend fun getWeatherTodayLatLng(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<CurrentWeather>

    @GET( "data/2.5/onecall")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("units") units: String = "metric",
        @Query("exclude",encoded = true) exclude: String = "current,daily,alerts,minutely",
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<ForecastWeather>

    @GET( "geo/1.0/direct")
    suspend fun getCityList(
        @Query("q") keyWord: String = "",
        @Query("limit") limit: Int = 10,
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<List<CityForecast>>
}
