package com.app.gdax

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

/**
 * Created by mohamed ibrahim on 7/28/2017.
 */

class OpenOrdersViewModel(app: Application) : AndroidViewModel(app) {
    val openOrders: LiveData<List<OpenOrder>> = MutableLiveData<List<OpenOrder>>()
        get () {
            if (field.value == null) {
                e("loaded open orders")
                field = db.openOrdersDao().loadOpenOrdersSync()
            }
            return field;
        }

}