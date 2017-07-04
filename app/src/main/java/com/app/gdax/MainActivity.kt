package com.app.gdax

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.support.v4.drawerLayout
import kotlin.concurrent.thread

class MainActivity : LifecycleActivity() {
    var drawer: DrawerLayout? = null

    val db = Room.databaseBuilder(this, AppDatabase::class.java, "GDAX").build()
    val socketManager = SocketHelper(db)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        thread {
            with(db.openOrdersDao()) {
                delete(getAll())
            }
        }

        drawer = drawerLayout {
            relativeLayout {
                backgroundColor = primaryColor

                val myAdapter = MyAdapter(db)
                val myLayoutManger = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                myLayoutManger.isItemPrefetchEnabled = false
                recyclerView {
                    adapter = myAdapter
                    db.matchOrdersDao().loadMatchedOrdersSync().observe(this@MainActivity, Observer {
                        // it == List<MatchOrder>
                        if (it != null) {
                            val trades = it.map { Trade(it.side == "sell", it.size, it.price, it.time) }
                            myAdapter.trades.clear()
                            myAdapter.trades.addAll(trades)
                            myAdapter.notifyDataSetChanged()
                        }
                    })
                    layoutManager = myLayoutManger

                }.lparams(width = matchParent)
            }.lparams(width = matchParent, height = matchParent)

            val navDrawer = NavDrawer(this@MainActivity, { drawer!!.closeDrawers() })
            navDrawer.lparams(width = dip(250), height = matchParent) {
                gravity = Gravity.START
            }
            this.addView(navDrawer)
        }
    }


    override fun onDestroy() {
        socketManager.shutDown()
        super.onDestroy()
    }
}
