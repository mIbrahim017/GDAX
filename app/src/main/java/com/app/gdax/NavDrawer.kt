package com.app.gdax

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by mohamed ibrahim on 7/4/2017.
 */
class NavDrawer(ctx: Context, action: () -> Unit) : _LinearLayout(ctx) {
    val items = arrayOf("Trade History", "Order Book", "Charts")

    init {
        orientation = VERTICAL
        backgroundColor = primaryColorLight

        view {
            backgroundColor = green
        }.lparams(width = matchParent, height = dip(100))

        items.forEach { item ->
            textView(item) {
                textColor = Color.WHITE
                textSize = 22f
                padding = dip(16)
                onClick {
                    action()
                    Toast.makeText(ctx, item, Toast.LENGTH_SHORT).show()

                }
            }.lparams(width = matchParent, height = dip(75))
        }
    }

}