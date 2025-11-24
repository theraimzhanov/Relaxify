package com.raimzhanov.sweatapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raimzhanov.sweatapp.databinding.ItemProductBinding
import com.raimzhanov.sweatapp.model.Product

class ProductAdapter(
    private val items: List<Product>,
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            ivProduct.setImageResource(item.image)
            tvName.text = item.name
            tvPrice.text = item.price
            tvDescription.text = item.description

            root.setOnClickListener { onClick(item) }
        }
    }
}
