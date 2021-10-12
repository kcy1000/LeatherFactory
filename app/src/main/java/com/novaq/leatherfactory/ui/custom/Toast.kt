package com.novaq.leatherfactory.ui.custom

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes

object Toast {
    fun createToast(activity: Activity, message: String, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(activity, message, duration).apply {
            setGravity(Gravity.CENTER, 0, 0)
        }
    }

    fun createToast(activity: Activity, @StringRes messageResId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(activity, messageResId, duration).apply {
            setGravity(Gravity.CENTER, 0, 0)
        }
    }

    fun createToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, message, duration).apply {
            setGravity(Gravity.CENTER, 0, 0)
        }
    }

    fun createToast(context: Context, @StringRes messageResId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, messageResId, duration).apply {
            setGravity(Gravity.CENTER, 0, 0)
        }
//        val inflater = LayoutInflater.from(context)
//        val binding: CustomToastBinding =
//            DataBindingUtil.inflate(inflater, R.layout.custom_toast, null, false)
//        binding.tvMessage.text = context.getText(messageResId)
//        return Toast(context).apply {
////            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 16.toPx())
//            setGravity(Gravity.CENTER, 0, 0)
//            //view = binding.root
//        }
    }
    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}