package com.raimzhanov.sweatapp.data.local

class OrderRepository(private val dao: OrderDao) {

    suspend fun insert(order: Order) = dao.insertOrder(order)

    suspend fun getAllOrders() = dao.getAllOrders()
}