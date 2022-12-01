package com.android.forecast.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.loadingFlow
import androidx.lifecycle.viewErrorFlow
import com.android.forecast.arch.extensions.collectFlow
import com.android.forecast.data.error.ErrorModel
import com.android.forecast.ui.widget.CustomProgressDialog


abstract class BaseFragment(@LayoutRes val layoutId: Int) : Fragment(layoutId) {

    private val progressDialog by lazy {
        CustomProgressDialog.newInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModel()?.run {
            collectFlow(viewErrorFlow) {
                (activity as? BaseActivity)?.handleCommonError(it)
            }

            collectFlow(loadingFlow) {
                handleProgressDialogStatus(it)
            }
        }
    }

    open fun handleApiError(errorModel: ErrorModel, onBtnClicked: () -> Unit = {}) {
        (activity as? BaseActivity)?.handleApiError(errorModel)
    }

    private fun handleProgressDialogStatus(isShow: Boolean) {
        if (isShow) {
            progressDialog.show(
                childFragmentManager,
                CustomProgressDialog::class.java.simpleName
            )
        } else {
            progressDialog.dismissAllowingStateLoss()
        }
    }

    abstract fun getViewModel(): BaseViewModel?
}
