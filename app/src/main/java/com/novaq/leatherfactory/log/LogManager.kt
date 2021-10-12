package com.novaq.leatherfactory.log

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object LogManager {
    val log:Log
        get() = Log()

    val logBuffer = LogBuffer()
}

interface LogObserver {
    fun update()
    fun update(index: Int)
}

class LogBuffer {
    private val buffer = arrayListOf<LogObj>()

    private val observers = arrayListOf<LogObserver>()

    fun register(observer: LogObserver): ArrayList<LogObj> {
        observers.add(observer)
        return buffer
    }

    fun unRegister(observer: LogObserver) {
        observers.remove(observer)
    }

    fun notifyChange() {
        observers.forEach { it.update() }
    }

    fun notifyChange(index: Int) {
        observers.forEach { it.update(index) }
    }

    fun put(log: LogObj) {
        synchronized(buffer) {
            buffer.add(log)
            notifyChange(buffer.lastIndex)
        }
    }

    fun clear() {
        buffer.clear()
        notifyChange()
    }
}

class LogObj(val level: Int, val str: String) {
    @SuppressLint("SimpleDateFormat")
    val timeStamp: String = SimpleDateFormat("HH:mm:ss").format(Date())
    override fun toString() = "[$timeStamp] ${TAG_LEVEL[level]} :: $str"
}
