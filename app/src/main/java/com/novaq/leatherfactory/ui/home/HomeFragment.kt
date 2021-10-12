package com.novaq.leatherfactory.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.novaq.leatherfactory.BR
import com.novaq.leatherfactory.R
import com.novaq.leatherfactory.databinding.FragmentHomeBinding
import com.novaq.leatherfactory.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getLayoutId() = R.layout.fragment_home

    override fun getBindingVariable() = BR.vm

    override val viewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}
