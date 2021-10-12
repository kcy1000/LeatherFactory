package com.novaq.leatherfactory.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.novaq.leatherfactory.BR
import com.novaq.leatherfactory.R
import com.novaq.leatherfactory.databinding.FragmentLoginBinding
import com.novaq.leatherfactory.log.LogManager
import com.novaq.leatherfactory.ui.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), LoginNavigator {

    override val viewModel by activityViewModels<LoginViewModel> ()

    override fun getLayoutId() = R.layout.fragment_login

    override fun getBindingVariable() = BR.vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigator = this
        viewModel.loginPw.value = "" //로그인페이지 진입시 패스워드는 삭제
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        viewModel.enableMediator.observe(viewLifecycleOwner, { validationResult ->
            //binding.btGoogleLogin.isEnabled = validationResult
        })

        viewModel.checkPw.observe(viewLifecycleOwner, { validationResult ->
            binding.tlPwArea.helperText = if(validationResult) "" else getString(R.string.pw_hint)
        })
    }

    override fun moveMainHome() {
        LogManager.log.d("moveMainHome")
        //Navigation.findNavController(binding.root).navigate(R.id.action_splashFragment_to_loginFragment2)
    }

    fun clickGoogleLogin() {
        getMainActivity().loginGoogle()
    }
}