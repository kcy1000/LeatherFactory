package com.novaq.leatherfactory.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:layout")
fun setLayout(viewGroup: ConstraintLayout, layoutId: Int?) {
    if (layoutId == 0 || layoutId == null) {
        return
    }
    val inflater: LayoutInflater = viewGroup.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    inflater.inflate(layoutId, viewGroup, false)?.let {
        viewGroup.removeAllViews()
        viewGroup.addView(it)
    }
}

@BindingAdapter("onSingleClick")
fun View.setOnSingleClickListener(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

