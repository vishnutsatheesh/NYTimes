package com.nytimes.util

import android.util.Log
import com.nytimes.BuildConfig


object LogsUtils {



    fun makeLogD(TAG: String, message: String?) {
        if (BuildConfig.LOG_STATUS)
            Log.d(TAG, "" + message)
    }

    fun makeLogE(TAG: String, message: String?) {
        if (BuildConfig.LOG_STATUS)
            Log.e(TAG, "" + message)
    }




}
/**
 * Create private constructor
 */