package com.raimzhanov.sweatapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raimzhanov.sweatapp.data.local.Order
import com.raimzhanov.sweatapp.data.local.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel(private val repo: OrderRepository) : ViewModel() {

    fun saveOrder(order: Order) {
        viewModelScope.launch {
            repo.insert(order)
        }
    }

    fun getOrders(onResult: (List<Order>) -> Unit) {
        viewModelScope.launch {
            onResult(repo.getAllOrders())
        }
    }
}

class OrderVMFactory(private val repo: OrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderViewModel(repo) as T
    }
}
