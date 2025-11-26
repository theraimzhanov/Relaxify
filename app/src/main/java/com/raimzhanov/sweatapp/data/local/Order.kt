package com.raimzhanov.sweatapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val orderNumber: String,
    val productName: String,
    val productImage: Int,
    val quantity: Int,
    val totalAmount: Double,

    val shippingAddress: String,
    val orderStatus: String,

    val date: Long
)

