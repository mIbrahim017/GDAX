package com.app.gdax

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

/**
 * Created by mohamed ibrahim on 7/21/2017.
 */
class TradeHistoryViewModel(app: Application) : AndroidViewModel(app) {

    var trads: LiveData<List<MatchOrder>> = MutableLiveData<List<MatchOrder>>()
        get() {

            if (field.value == null) {
                e("loaded trade history")
                field = db.matchOrdersDao().loadMatchedOrdersSync()
            }
            return field;
        }

}