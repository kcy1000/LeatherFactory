package com.novaq.leatherfactory.ui.splash

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.novaq.leatherfactory.BR
import com.novaq.leatherfactory.R
import com.novaq.leatherfactory.data.DataStorageController
import com.novaq.leatherfactory.databinding.FragmentSplashBinding
import com.novaq.leatherfactory.ui.base.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(), SplashNavigator {

    override val viewModel by activityViewModels<SplashViewModel> ()

    override fun getLayoutId() = R.layout.fragment_splash

    override fun getBindingVariable() = BR.vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataStorageController.init(requireContext())

        viewModel.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                viewModel.startProcess()
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}

        })
    }

    override fun moveLogin() {
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_splashFragment_to_loginFragment2)
    }

    override fun moveMainHome() {
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_splashFragment_to_loginFragment2)
    }

}