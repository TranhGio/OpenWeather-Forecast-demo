package com.android.forecast.ui.base

import androidx.lifecycle.ViewModel
import com.android.forecast.arch.extensions.LoadingAware
import com.android.forecast.arch.extensions.ViewErrorAware

open class BaseViewModel : ViewModel(), ViewErrorAware, LoadingAware
