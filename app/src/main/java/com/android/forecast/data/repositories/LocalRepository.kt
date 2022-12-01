package com.android.forecast.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject


class LocalRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : LocalDataSource {
    companion object {
        internal const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
        internal const val KEY_TERM_ACCEPT = "KEY_TERM_ACCEPT"
    }

    override fun getAccessToken(): String {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, "") ?: ""
    }

    override fun saveAccessToken(accessToken: String?) {
        sharedPreferences.edit(true) {
            putString(KEY_ACCESS_TOKEN, accessToken)
        }
    }

    override fun getTermAccept(): Boolean {
        return sharedPreferences.getBoolean(KEY_TERM_ACCEPT, false)
    }

    override fun saveTermAccept(status: Boolean?) {
        sharedPreferences.edit(true) {
            putBoolean(KEY_TERM_ACCEPT, status ?: false)
        }
    }
}


