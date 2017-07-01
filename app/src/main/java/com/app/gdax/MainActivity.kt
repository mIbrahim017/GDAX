package com.app.gdax

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val db = Room.databaseBuilder(this, AppDatabase::class.java, "GDAX").build()
    val socketManager = SocketHelper(db)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        thread {
            with(db.openOrdersDao()) {
                delete(getAll())
            }
        }


        relativeLayout {
            textView("Hello!")

            button("Log database") {
                onClick {
                    thread {
                        db.openOrdersDao().getAll().forEach { e(it) }
                    }
                }
            }
        }


    }


    override fun onDestroy() {
        socketManager.shutDown()
        super.onDestroy()
    }
}
