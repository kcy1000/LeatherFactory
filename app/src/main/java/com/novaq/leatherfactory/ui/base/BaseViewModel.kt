package com.novaq.leatherfactory.ui.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {
    private var _navigator: WeakReference<N>? = null
    var navigator: N?
        get() = this._navigator?.get()
        set(value) {
            this._navigator = WeakReference(value)
        }

    /*private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    fun isLoading(): Boolean {
        return _isLoading.value == true
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }*/
}