package com.app.gdax

import android.content.Context
import android.graphics.Color
import android.util.Log

/**
 * Created by mohamed ibrahim on 6/29/2017.
 */



val green = Color.parseColor("#70ce5c")
val red = Color.parseColor("#ff6939")
val primaryColor = Color.parseColor("#1e2b34")
val primaryColorLight = Color.parseColor("#2F3D45")

fun Any.e(any: Any? = "no message provided") {
    Log.e(this.javaClass.simpleName + "~", any.toString())
}

