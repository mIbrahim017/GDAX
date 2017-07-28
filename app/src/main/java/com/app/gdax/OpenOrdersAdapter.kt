package com.app.gdax

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.*

/**
 * Created by mohamed ibrahim on 7/28/2017.
 */

data class Order(val isBid: Boolean, val size: Float, val price: Float)

class OpenOrdersAdapter : RecyclerView.Adapter<OpenOrdersAdapter.ViewHolder>() {

    val openOrders = mutableListOf<Order>()

    override fun getItemCount() = openOrders.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenOrdersAdapter.ViewHolder {
        val view = parent?.include<ConstraintLayout>(R.layout.completed_trade)
        return OpenOrdersAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OpenOrdersAdapter.ViewHolder, position: Int) {
        holder.update(openOrders[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val sizeBar = view.find<View>(R.id.sizeBar)
        val sizeTextView = view.find<TextView>(R.id.sizeTextView)
        val priceTextView = view.find<TextView>(R.id.priceTextView)

        fun update(order: Order) {
            val color = if (order.isBid) green else red
            sizeBar.backgroundColor = color
            val viewWidth = view.dip(if (order.size > 80) 80f else order.size + 1f)
            sizeBar.layoutParams = ConstraintLayout.LayoutParams(viewWidth, 0)
            sizeTextView.text = formatNumString(order.size, 8)
            priceTextView.text = formatNumString(order.price, 2)
            priceTextView.textColor = color

        }


    }


}