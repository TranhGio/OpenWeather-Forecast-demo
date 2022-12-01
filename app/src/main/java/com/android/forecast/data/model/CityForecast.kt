package com.android.forecast.data.model

import com.android.forecast.data.network.response.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityForecast(
    @SerialName("name") val name: String? = "",
    @SerialName("lat") val lat: Float? = 0.0f,
    @SerialName("lon") val long: Float? = 0.0f,
    @SerialName("country") val country: String? = ""
) : BaseResponse()
