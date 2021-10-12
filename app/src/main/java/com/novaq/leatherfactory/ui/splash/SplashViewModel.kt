package com.novaq.leatherfactory.ui.splash

import com.novaq.leatherfactory.data.DataStorageController
import com.novaq.leatherfactory.log.LogManager
import com.novaq.leatherfactory.ui.base.BaseNavigator
import com.novaq.leatherfactory.ui.base.BaseViewModel


class SplashViewModel : BaseViewModel<SplashNavigator>() {

    fun startProcess() {
        LogManager.log.d("startProcess()!!!")

        val isAutoLogin = DataStorageController.getInstance().isAutoLogin
        if(isAutoLogin) {
            autoLogin()
        }
        else {
            navigator?.moveLogin()
        }
    }

    /**
     * ID, PW 입력하는 UI가 없을 뿐 그냥 login 과 동일하다.
     */
    fun autoLogin() {
        LogManager.log.d("Auto Login")

        /*val pref = AppPosApplication.pref

        navigator?.showLoading()
        viewModelScope.launch viewModelScope@ {
            val repository = LoginRepository.getInstance()
            val result = repository.login(pref.userID, pref.userPassword)
            navigator?.hideLoading()
            LogManager.log.d("Login response $result")
            when (result) {
                is ResponseOutput.Success -> {
                    if (!TextUtils.equals(result.output.code, "0")) {
                        LogManager.log.w("Fail Code:[${result.output.code}]${result.output.message}")
                        navigator?.showErrorToast(result.output.message ?: "${result.output.code} error")
                        navigator?.moveLogin()
                        return@viewModelScope
                    }
                    //여기서부터 로그인 성공
                    AppPosApplication.authToken = result.output.token
                    navigator?.moveMainHome()
                }
                is ResponseOutput.Error -> {
                    LogManager.log.e("LoginFail response.code ${result.code}")
                    navigator?.showErrorToast(result.message)
                    navigator?.moveLogin()
                }
                is ResponseOutput.Exception -> {
                    LogManager.log.e("Login exception \n${result.exception}")
                    navigator?.showErrorToast(R.string.network_error_message)
                    navigator?.moveLogin()
                }
            }
        }*/
    }
}


/**
 * ViewModel에서 처리 할 수 없는 context관련 Fragment로 전달할 동작상태를 기술한다.
 * ex)다른 activity띄우기, fragment전환하기, popup띄우기 등등
 */
interface SplashNavigator : BaseNavigator {
    fun moveMainHome()
    fun moveLogin()
}
