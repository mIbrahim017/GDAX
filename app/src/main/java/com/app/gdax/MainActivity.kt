package com.app.gdax

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.support.v4.drawerLayout
import kotlin.concurrent.thread

class MainActivity : LifecycleActivity() {

    lateinit var drawer: DrawerLayout


    fun clearDatabase() {
        thread {
            with(db.receivedOrdersDao()) { this.delete(this.getAll()) }
            with(db.openOrdersDao()) { this.delete(this.getAll()) }
            with(db.changeOrdersDao()) { this.delete(this.getAll()) }
            with(db.doneOrdersDao()) { this.delete(this.getAll()) }
            with(db.matchOrdersDao()) { this.delete(this.getAll()) }
        }
    }

    fun logDatabase() {
        thread {
            db.receivedOrdersDao().getAll().forEach { e(it) }
            db.openOrdersDao().getAll().forEach { e(it) }
            db.changeOrdersDao().getAll().forEach { e(it) }
            db.doneOrdersDao().getAll().forEach { e(it) }
            db.matchOrdersDao().getAll().forEach { e(it) }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(SocketHelper())
        val viewModel = ViewModelProviders.of(this@MainActivity).get(TradeHistoryViewModel::class.java)


        drawer = drawerLayout {

            coordinatorLayout {
                backgroundColor = primaryColor

                appBarLayout {
                    val toolbar = toolbar {
                        title = "GDAX"
                        setTitleTextColor(Color.WHITE)
                        backgroundColor = primaryColorLight
                    }.lparams(width = matchParent, height = dip(50))
                }.lparams(width = matchParent)

                recyclerView {
                    val myAdapter = MyAdapter(db)
                    adapter = myAdapter

                    viewModel.trads.observe(this@MainActivity , Observer {

                        if(it != null){
                            val trades = it.map { Trade(it.side == "sell", it.size, it.price, it.time) }
                            myAdapter.trades.clear()
                            myAdapter.trades.addAll(trades)
                            myAdapter.notifyDataSetChanged()
                        }
                    })


                    val myLayoutManger = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                    myLayoutManger.isItemPrefetchEnabled = false
                    layoutManager = myLayoutManger

                }.lparams(width = matchParent)


            }.lparams(width = matchParent, height = matchParent)

            val navDrawer = NavDrawer(this@MainActivity, { drawer.closeDrawers() })
            navDrawer.lparams(width = dip(250), height = matchParent) {
                gravity = Gravity.START
            }
            this.addView(navDrawer)
        }


//        drawer = drawerLayout {
//            relativeLayout {
//                backgroundColor = primaryColor
//
//                val myAdapter = MyAdapter(db)
//                val myLayoutManger = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
//                myLayoutManger.isItemPrefetchEnabled = false
//                recyclerView {
//                    adapter = myAdapter
//                    db.matchOrdersDao().loadMatchedOrdersSync().observe(this@MainActivity, Observer {
//                        // it == List<MatchOrder>
//                        if (it != null) {
//                            val trades = it.map { Trade(it.side == "sell", it.size, it.price, it.time) }
//                            myAdapter.trades.clear()
//                            myAdapter.trades.addAll(trades)
//                            myAdapter.notifyDataSetChanged()
//                        }
//                    })
//                    layoutManager = myLayoutManger
//
//                }.lparams(width = matchParent)
//            }.lparams(width = matchParent, height = matchParent)
//
//            val navDrawer = NavDrawer(this@MainActivity, { drawer!!.closeDrawers() })
//            navDrawer.lparams(width = dip(250), height = matchParent) {
//                gravity = Gravity.START
//            }
//            this.addView(navDrawer)
//        }
    }


}
