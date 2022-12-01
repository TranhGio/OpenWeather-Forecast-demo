package com.android.forecast.data.model

import android.os.Parcelable
import com.android.forecast.data.network.response.BaseResponse
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CurrentWeather(
    @SerialName("coord") val coord: Coord? = Coord(),
    @SerialName("weather") val weather: List<Weather>? = mutableListOf(),
    @SerialName("main") val main: Main? = Main(),
    @SerialName("name") val name: String? = ""
) : Parcelable, BaseResponse()

@Parcelize
@Serializable
data class Coord(
    @SerialName("lon") val lon: Float? = 0f,
    @SerialName("lat") val lat: Float? = 0f,
) : Parcelable

@Parcelize
@Serializable
data class Weather(
    @SerialName("id") val id: Int? = 0,
    @SerialName("main") val main: String? = "",
    @SerialName("description") val description: String? = "",
    @SerialName("icon") val icon: String? = ""
) : Parcelable

@Parcelize
@Serializable
data class Main(
    @SerialName("temp") val temp: Float? = 0.0F,
    @SerialName("feels_like") val feels_like: Float? = 0.0F,
    @SerialName("temp_min") val temp_min: Float? = 0.0F,
    @SerialName("temp_max") val temp_max: Float? = 0.0F,
    @SerialName("humidity") val humidity: Float? = 0.0F
) : Parcelable
