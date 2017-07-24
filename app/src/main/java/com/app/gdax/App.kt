package com.app.gdax

import android.app.Application
import android.arch.persistence.room.Room

/**
 * Created by mohamed ibrahim on 7/21/2017.
 */



val db by lazy{
    App.db !!
}
class App : Application(){
    companion  object {
        var db : AppDatabase ? =null
    }
      
	 override fun onCreate() {
        db = Room.databaseBuilder(this ,AppDatabase::class.java ,"GDAX").build()
        super.onCreate()
    }
}
