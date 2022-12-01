package com.android.forecast.data.repositories


interface LocalDataSource {
    fun getAccessToken(): String
    fun saveAccessToken(accessToken: String?)
    fun getTermAccept(): Boolean
    fun saveTermAccept(status: Boolean?)
}
