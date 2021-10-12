package com.novaq.leatherfactory.util

import android.view.View
import java.util.concurrent.atomic.AtomicBoolean

class OnSingleClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 1000
) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed({
                    canClick.set(true)
                }, interval)
                clickListener.onClick(v)
            }
        }
    }
}