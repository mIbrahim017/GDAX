package com.app.gdax

import android.util.Log

/**
 * Created by mohamed ibrahim on 6/29/2017.
 */

fun Any.e(any: Any? = "no message provided") {
    Log.e(this.javaClass.simpleName + "~", any.toString())
}