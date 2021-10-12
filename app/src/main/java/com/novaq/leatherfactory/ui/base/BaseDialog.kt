package com.novaq.leatherfactory.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.novaq.leatherfactory.MainActivity
import com.novaq.leatherfactory.ui.custom.SimpleDialog
import com.novaq.leatherfactory.ui.custom.Toast


abstract class BaseDialog : DialogFragment() {

    /** 메인액티비티 접근시 requierActivity 하고 MainActivity까지 캐스팅 하는게 타이핑이 귀찮아서 만듦 */
    private var activity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            activity = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        // creating the fullscreen dialog
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun show(fragmentManager: FragmentManager, tag: String?) {
        val transaction = fragmentManager.beginTransaction()
        fragmentManager.findFragmentByTag(tag)?.apply {
            transaction.remove(this)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    fun dismissDialog(tag: String?) {
        dismiss()
    }

    fun showErrorPopup(@StringRes errorMessageEmptyData: Int) {
        SimpleDialog()
                .setMessage(errorMessageEmptyData)
                .setDismissFunction { requireActivity().finishAffinity() }
                .show(parentFragmentManager)
    }

    fun showErrorToast(@StringRes errorMessageEmptyData: Int) {
        val errorMessage = getString(errorMessageEmptyData)
        Toast.createToast(requireActivity(), errorMessage).show()
    }

    fun showErrorToast(errorMessageEmptyData: String) = Toast.createToast(requireActivity(), errorMessageEmptyData).show()

    fun hideKeyboard() = activity?.hideKeyboard()

    fun hideLoading() = activity?.hideLoading()

    val isNetworkConnected = activity?.isNetworkConnected()

    fun showLoading() = activity?.showLoading()
}