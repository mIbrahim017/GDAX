package com.app.gdax

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by mohamed ibrahim on 6/29/2017.
 */


/**
 *  Open Order table and Data Access
 */
@Entity(tableName = "OPEN_ORDERS")
data class OpenOrder(
        @PrimaryKey var sequence: Int,
        @ColumnInfo var type: String,
        @ColumnInfo var time: String,
        @ColumnInfo var order_id: String,
        @ColumnInfo var price: Float,
        @ColumnInfo var remaining_size: Float,
        @ColumnInfo var side: String)


@Dao
interface OpenOrdersDao {
    @Query("SELECT * FROM OPEN_ORDERS ORDER BY SEQUENCE DESC")
    fun loadOpenOrdersSync(): LiveData<List<OpenOrder>>

    @Query("SELECT * FROM OPEN_ORDERS ORDER By PRICE DESC")
    fun getAll(): List<OpenOrder>

    @Insert
    fun insert(model: OpenOrder)

    @Delete
    fun delete(models: List<OpenOrder>)
}

/**
 *  Received Order table and Data Access
 */
@Entity(tableName = "RECEIVED_ORDERS")
data class ReceivedOrder(
        @PrimaryKey var sequence: Int,
        @ColumnInfo var type: String,
        @ColumnInfo var time: String,
        @ColumnInfo var order_id: String,
        @ColumnInfo var size: String,
        @ColumnInfo var price: String,
        @ColumnInfo var funds: String,
        @ColumnInfo var side: String,
        @ColumnInfo var order_type: String
)


@Dao
interface ReceivedOrdersDao {
    @Query("SELECT * FROM RECEIVED_ORDERS ORDER By PRICE DESC")
    fun getAll(): List<ReceivedOrder>

    @Insert
    fun insert(model: ReceivedOrder)

    @Delete
    fun delete(models: List<ReceivedOrder>)
}


/**
 * Done  Order Table and Data Access
 */

@Entity(tableName = "DONE_ORDERS")
data class DoneOrder(@PrimaryKey var sequence: Int = 0,
                     @ColumnInfo var type: String,
                     @ColumnInfo var time: String,
                     @ColumnInfo var price: String,
                     @ColumnInfo var order_id: String,
                     @ColumnInfo var reason: String,
                     @ColumnInfo var side: String,
                     @ColumnInfo var remaining_size: String)


@Dao
interface DoneOrdersDao {
    @Query("SELECT * FROM DONE_ORDERS ORDER By PRICE DESC")
    fun getAll(): List<DoneOrder>

    @Insert
    fun insert(model: DoneOrder)

    @Delete
    fun delete(models: List<DoneOrder>)
}


/**
 * Match table and Data Access
 */
@Entity(tableName = "MATCH_ORDERS")
data class MatchOrder(
        @PrimaryKey var sequence: Int = 0,
        @ColumnInfo var type: String,
        @ColumnInfo var trade_id: Int = 0,
        @ColumnInfo var maker_order_id: String,
        @ColumnInfo var taker_order_id: String,
        @ColumnInfo var time: String,
        @ColumnInfo var size: Float = 0f,
        @ColumnInfo var price: Float = 0f,
        @ColumnInfo var side: String
)


@Dao
interface MatchOrdersDao {

    @Query("SELECT * FROM MATCH_ORDERS ORDER BY PRICE DESC")
    fun getAll(): List<MatchOrder>

    @Insert
    fun insert(matchOrder: MatchOrder)

    @Delete
    fun delete(matchOrders: List<MatchOrder>)


    @Query("SELECT * FROM MATCH_ORDERS ORDER BY SEQUENCE DESC")
    fun loadMatchedOrdersSync(): LiveData<List<MatchOrder>>
}


/**
 * Change Table and Data Access
 */
@Entity(tableName = "CHANGE_ORDERS")
data class ChangeOrder(
        @PrimaryKey var sequence: Int = 0,
        @ColumnInfo var type: String,
        @ColumnInfo var time: String,
        @ColumnInfo var order_id: String,
        @ColumnInfo var new_size: String,
        @ColumnInfo var old_size: String,
        @ColumnInfo var price: String,
        @ColumnInfo var side: String
)


@Dao
interface ChangeOrdersDao {
    @Query("SELECT * FROM CHANGE_ORDERS ORDER BY PRICE DESC")
    fun getAll(): List<ChangeOrder>

    @Insert
    fun insert(changeOrder: ChangeOrder)

    @Delete
    fun delete(changeOrders: List<ChangeOrder>)
}


@Database(entities = arrayOf(ChangeOrder::class, DoneOrder::class, MatchOrder::class,
        OpenOrder::class, ReceivedOrder::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun openOrdersDao(): OpenOrdersDao
    abstract fun changeOrdersDao(): ChangeOrdersDao
    abstract fun doneOrdersDao(): DoneOrdersDao
    abstract fun matchOrdersDao(): MatchOrdersDao
    abstract fun receivedOrdersDao(): ReceivedOrdersDao
}


