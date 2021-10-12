package com.novaq.leatherfactory.ui.base

import androidx.annotation.StringRes

interface BaseNavigator {
    fun showErrorPopup(errorMessageEmptyData: String?)
    fun showErrorPopup(@StringRes errorMessageEmptyData: Int)
    fun showErrorToast(errorMessageEmptyData: String?)
    fun showErrorToast(@StringRes errorMessageEmptyData: Int)
    fun showLoading()
    fun hideLoading()
}