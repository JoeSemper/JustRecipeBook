package com.joesemper.justrecipebook.util.logger

import android.util.Log
import com.joesemper.justrecipebook.BuildConfig.DEBUG

class Logger : ILogger {

    private val TAG = "MEAL"

    override fun log(throwable: Throwable) {
        if (DEBUG) {
            Log.v(TAG, throwable.message.toString())
        }
    }
}