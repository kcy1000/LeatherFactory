package com.novaq.leatherfactory.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner

/**
 * hison
 * 기본스피너는 ItemSelected 에서
 * 유저가 선택한것과 프로그램으로 set한것의 구분이 되지 않아 만들었음.
 */
class Spinner : AppCompatSpinner, AdapterView.OnItemSelectedListener {
    var listener: OnItemSelectedListener? = null

    private var lastPosition = 0

    /**
     * used to ascertain whether the user selected an item on spinner (and not programmatically)
     */
    private var isUserActionOnSpinner = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        isUserActionOnSpinner = true
        return super.onTouchEvent(event)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        listener?.onItemSelected(parent, view, position, id, isUserActionOnSpinner)
        isUserActionOnSpinner = false
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        listener?.onNothingSelected(parent)
        isUserActionOnSpinner = false
    }

    interface OnItemSelectedListener {

        fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long, userSelected: Boolean)

        fun onNothingSelected(parent: AdapterView<*>)
    }

    fun programmaticallySetPosition(pos: Int, animate: Boolean) {
        //isUserActionOnSpinner = false
        setSelection(pos, animate)
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
        this.listener = listener
    }

    override fun setSelection(position: Int, animate: Boolean) {
        val isSameSelected = lastPosition == selectedItemPosition
        val onItemSelectedListener = listener
        if (isSameSelected && onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(this, selectedView, position, selectedItemId, isUserActionOnSpinner)
        }
        lastPosition = position

        super.setSelection(position, animate)
    }

    override fun setSelection(position: Int) {
        val isSameSelected = lastPosition == selectedItemPosition
        val onItemSelectedListener = listener
        if (isSameSelected && onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(this, selectedView, position, selectedItemId, isUserActionOnSpinner)
        }
        lastPosition = position

        super.setSelection(position)
    }

    constructor(context: Context) : super(context) {
        super.setOnItemSelectedListener(this)
    }

    constructor(context: Context, mode: Int) : super(context, mode) {
        super.setOnItemSelectedListener(this)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        super.setOnItemSelectedListener(this)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        super.setOnItemSelectedListener(this)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int, mode: Int) : super(context, attrs, defStyle, mode) {
        super.setOnItemSelectedListener(this)
    }
}