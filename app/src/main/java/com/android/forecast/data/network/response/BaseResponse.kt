package com.android.forecast.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class BaseResponse(
    @SerialName("code") val code : String = "",
    @SerialName("message") val message : String = ""
)
