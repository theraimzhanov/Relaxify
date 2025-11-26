package com.raimzhanov.sweatapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrder(order: Order)

    @Query("SELECT * FROM orders ORDER BY date DESC")
    suspend fun getAllOrders(): List<Order>
}
