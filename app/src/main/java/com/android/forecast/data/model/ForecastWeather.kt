package com.android.forecast.data.model

import android.os.Parcelable
import com.android.forecast.data.network.response.BaseResponse
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ForecastWeather(
    @SerialName("timezone") val timezone: String? = "",
    @SerialName("lon") val lon: String? = "",
    @SerialName("lat") val lat: String? = "",
    @SerialName("hourly") val hourly: MutableList<Hourly>? = mutableListOf(),
) : Parcelable, BaseResponse()

@Parcelize
@Serializable
data class Hourly(
    @SerialName("dt") val dt: Long? = 0,
    @SerialName("temp") val temp: Double? = 0.0,
    @SerialName("humidity") val humidity: Int? = 0,
) : Parcelable {
    @SerialName("temperatureType") var temperatureType: Int? = 0
}
