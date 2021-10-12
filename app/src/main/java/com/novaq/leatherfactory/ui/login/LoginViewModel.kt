package com.novaq.leatherfactory.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.novaq.leatherfactory.data.DataStorageController
import com.novaq.leatherfactory.ui.base.BaseNavigator
import com.novaq.leatherfactory.ui.base.BaseViewModel
import com.novaq.leatherfactory.util.PasswordUtil


class LoginViewModel : BaseViewModel<LoginNavigator>() {

    val loginId = MutableLiveData(DataStorageController.getInstance().userID)
    val loginPw = MutableLiveData("")

    private val _enableMediator = MediatorLiveData<Boolean>()
    val enableMediator : LiveData<Boolean> get() = _enableMediator
    init {
        _enableMediator.addSource(loginId) { checkEnable() }
        _enableMediator.addSource(loginPw) { checkEnable() }
    }

    private val _checkPw = MediatorLiveData<Boolean>()
    val checkPw : LiveData<Boolean> get() = _checkPw
    init {
        _checkPw.addSource(loginPw) { checkError() }
    }

    fun login(id: String, encryptedPassword: String) {
        navigator?.showLoading()

    }

    private fun checkEnable() {
        val id: String = (loginId.value ?: "").trim()
        val password: String = (loginPw.value ?: "").trim()
        _enableMediator.value = id.length > 5 && _checkPw.value == true
    }

    private fun checkError() {
        val pw = (loginPw.value ?: "").trim()
        _checkPw.value = PasswordUtil().isPasswordValid(pw)
    }

    fun clickEmailLogin() {
        val id: String = (loginId.value ?: "").trim()
        val password: String = (loginPw.value ?: "").trim()
        if(id.isNotEmpty() && password.isNotEmpty()) {
            login(id, password)
        }
    }


}

/**
 * ViewModel에서 처리 할 수 없는 context관련 Fragment로 전달할 동작상태를 기술한다.
 * ex)다른 activity띄우기, fragment전환하기, popup띄우기 등등
 */
interface LoginNavigator : BaseNavigator {
    fun moveMainHome()
}
