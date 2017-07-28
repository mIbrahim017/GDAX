package com.app.gdax

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.widget.Toast
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by mohamed ibrahim on 7/4/2017.
 */

data class NavDrawerEntry(val title: String, val fragment: Fragment)

val navDrawerItems = arrayOf(
                NavDrawerEntry("Trade History", TradeHistoryFragment()),
                NavDrawerEntry("Order Book", OpenOrdersFragment()),
               NavDrawerEntry("Charts", OpenOrdersFragment()))


class NavDrawer(ctx: Context, action: (NavDrawerEntry) -> Unit) : _LinearLayout(ctx) {


    init {
        orientation = VERTICAL
        backgroundColor = primaryColorLight

        view {
            backgroundColor = green
        }.lparams(width = matchParent, height = dip(100))

        navDrawerItems.forEach { entry  ->
            textView(entry.title) {
                textColor = Color.WHITE
                textSize = 22f
                padding = dip(16)
                onClick {
                    action(entry)
                    Toast.makeText(ctx, entry.title, Toast.LENGTH_SHORT).show()

                }
            }.lparams(width = matchParent, height = dip(75))
        }
    }

}