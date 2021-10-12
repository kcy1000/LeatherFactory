package com.novaq.leatherfactory.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.novaq.leatherfactory.R
import com.novaq.leatherfactory.databinding.DialogSimpleBinding
import com.novaq.leatherfactory.ui.base.BaseDialog


/**
 * 단순 알림용 팝업에 사용 될 팝업이다.
 * 라운딩 처리된 팝업이 필요해서 만든것이므로 문구만 변경 가능하도록 하였다.
 * 거의 토스트와 비슷하므로 딱히 model은 만들지 않았다.
 */

/* 사용법 예시
    SimpleDialog()
    .setMessage(R.string.error_auth_fail)
    .setDismissFunction { requireActivity().finishAffinity() }
    .show(parentFragmentManager)
*/

//@Navigator.Name("dialog_fragment")
class SimpleDialog : BaseDialog() {
    private var whenDismissFunc: (() -> Unit)? = null
    private val TAG = "SimpleDialog"
    private lateinit var binding : DialogSimpleBinding
    private var messageRedId: Int? = null
    private var messageString: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_simple, container, false)

        messageString?.apply { binding.tvMessage.text = this }
        messageRedId?.apply { binding.tvMessage.setText(this) }

        binding.btConfirm.setOnClickListener{
            whenDismissFunc?.let { it() }
            dismissDialog()
        }
        return binding.root
    }

    fun setMessage(@StringRes resId: Int) : SimpleDialog{
        dialog?.let {
            binding.tvMessage.setText(resId)
        }
        messageRedId = resId

        return this
    }

    fun setMessage(msg: String) : SimpleDialog{
        dialog?.let {
            binding.tvMessage.text = msg
        }
        messageString = msg

        return this
    }

    fun setDismissFunction(whenDismissFunc : () -> Unit) : SimpleDialog{
        dialog?.let {
            binding.btConfirm.setOnClickListener{
                whenDismissFunc()
                dismissDialog()
            }
        }
        this.whenDismissFunc = whenDismissFunc

        return this
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)
    fun dismissDialog() = dismissDialog(TAG)
}