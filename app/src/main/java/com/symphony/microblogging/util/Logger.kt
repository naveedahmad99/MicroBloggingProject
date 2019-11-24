package com.symphony.microblogging.util

import android.util.Log
import com.symphony.microblogging.BuildConfig

object Logger {

    fun <T : Any> d(clazz: T, msg: String) {
        if (BuildConfig.QA) {
            Log.d(clazz::class.java.simpleName, msg)
        }
    }

    fun e(t: Throwable) {
        if (BuildConfig.QA) {
            t.printStackTrace()
        }
    }
}