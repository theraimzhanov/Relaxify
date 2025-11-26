package com.raimzhanov.sweatapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raimzhanov.sweatapp.data.local.Order
import com.raimzhanov.sweatapp.databinding.ItemOrderBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderHistoryAdapter :
    RecyclerView.Adapter<OrderHistoryAdapter.OrderVH>() {

    private val list = ArrayList<Order>()

    fun submitList(newList: List<Order>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class OrderVH(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.ivOrderImage.setImageResource(order.productImage)
            binding.tvOrderName.text = order.productName
            binding.tvOrderAmount.text = "$${order.totalAmount}"
            binding.tvOrderStatus.text = order.orderStatus
            binding.tvOrderNumber.text = "Order #${order.orderNumber}"

            val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                .format(Date(order.date))
            binding.tvOrderDate.text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderVH {
        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderVH(binding)
    }

    override fun onBindViewHolder(holder: OrderVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}
