package com.novaq.leatherfactory.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.novaq.leatherfactory.MainActivity
import com.novaq.leatherfactory.ui.custom.SimpleDialog
import com.novaq.leatherfactory.ui.custom.Toast

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel<*>> : Fragment(), BaseNavigator {

    private lateinit var activity: MainActivity
    lateinit var binding: B
    abstract val viewModel: VM

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    /** 메인액티비티 접근시 requierActivity 하고 MainActivity까지 캐스팅 하는게 타이핑이 귀찮아서 만듦 */
    open fun getMainActivity() = activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            this.activity = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(getBindingVariable(), viewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    fun isNetworkConnected() = activity.isNetworkConnected()

    fun hideKeyboard() = activity.hideKeyboard()

    override fun hideLoading() = activity.hideLoading()

    override fun showLoading() = activity.showLoading()

    override fun showErrorPopup(errorMessageEmptyData: String?) {
        SimpleDialog()
            .setMessage(errorMessageEmptyData ?: "")
            .setDismissFunction { requireActivity().finishAffinity() }
            .show(parentFragmentManager)
    }

    override fun showErrorPopup(@StringRes errorMessageEmptyData: Int) {
        SimpleDialog()
                .setMessage(errorMessageEmptyData)
                .setDismissFunction { requireActivity().finishAffinity() }
                .show(parentFragmentManager)
    }

    override fun showErrorToast(@StringRes errorMessageEmptyData: Int) {
        val errorMessage = getString(errorMessageEmptyData)
        Toast.createToast(activity, errorMessage).show()
    }

    override fun showErrorToast(errorMessageEmptyData: String?) {
        Toast.createToast(activity, errorMessageEmptyData ?: "").show()
    }

    fun navigateUp() {
        findNavController().navigateUp()
    }

    /*fun getViewModelFactory(repository: BaseRepository): ViewModelFactory {
        return ViewModelFactory(repository, requireActivity())
    }

    fun getViewModelFactory(): ViewModelFactoryWithoutRepository {
        return ViewModelFactoryWithoutRepository(requireActivity())
    }*/

}