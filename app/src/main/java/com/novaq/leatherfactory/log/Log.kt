package com.novaq.leatherfactory.log

import android.util.Log
import com.novaq.leatherfactory.BuildConfig

const val LEVEL_V = 0
const val LEVEL_D = 1
const val LEVEL_I = 2
const val LEVEL_W = 3
const val LEVEL_E = 4
val TAG_LEVEL = arrayOf("V", "D", "I", "W", "E")

/**
 * JVM 상에서 로그를 출력하기 위한 Log 의 구현체
 */
class  Log {

    val TAG = "Hison"
    var buffer = ArrayList<LogObj>()
    val maxLevel = if (BuildConfig.DEBUG) -1 else LEVEL_E

    /*var mOption: LogOption = object : LogOption {
        override fun checkLogLevel(level: Int) = (level > LEVEL_E)
    }*/

    private fun checkLogLevel(level: Int) = (level > maxLevel)

    fun v(message: String) {
        log(LEVEL_V, message)
    }

    fun d(message: String) {
        log(LEVEL_D, message)
    }

    fun i(message: String) {
        log(LEVEL_I, message)
    }

    fun w(message: String) {
        log(LEVEL_W, message)
    }

    fun e(message: String) {
        log(LEVEL_E, message)
    }

    /*private fun log(level: Int, message: String) {
        if (checkLogLevel(level)) {
            println("$TAG/${TAG_LEVEL[level]} :: $message")
        }
    }

    private fun elog(level: Int, message: String) {
        if (checkLogLevel(level)) {
            System.err.println("$TAG/${TAG_LEVEL[level]} :: $message")
        }
    }

    override fun printStackTrace(exception: Throwable) {
        w("${exception.stackTrace[0]}")
    }*/

    private fun log(level: Int, message: String) {
        synchronized(this) {
            if (checkLogLevel(level)) {
                when (level) {
                    LEVEL_V -> Log.v(TAG, message)
                    LEVEL_D -> Log.d(TAG, message)
                    LEVEL_W -> Log.w(TAG, message)
                    LEVEL_I -> Log.i(TAG, message)
                    LEVEL_E -> Log.e(TAG, message)
                }
                buffer.add(LogObj(level, message))
                try {
//            m.getBlock(WebDebug::class.java).logObserver?.updateLog("$buffer")?.let{
                    LogManager.logBuffer.let {
                        buffer.forEach { l ->
                            it.put(l)
                        }
                    }
                    buffer.clear()
                } catch (e: Exception) {
                }
            }
        }
    }

    fun printStackTrace(exception: Throwable) {
        val message = "${exception.message}\n${exception.stackTrace[0]}"
        w(message)
        exception.printStackTrace()
        synchronized(this){
            buffer.add(LogObj(LEVEL_W, message))
            try {
                LogManager.logBuffer.let{
                    buffer.forEach { l ->
                        it.put(l)
                    }
                }
                buffer.clear()
            } catch (e : Exception) { }
        }
    }
}