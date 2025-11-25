package com.raimzhanov.sweatapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raimzhanov.sweatapp.databinding.ItemGridBinding
import com.raimzhanov.sweatapp.model.Product

class ProductGridAdapter(
    private val list: List<Product>,
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductGridAdapter.ProductVH>() {

    inner class ProductVH(val binding: ItemGridBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.ivProduct.setImageResource(product.image)
            binding.tvName.text = product.name
            binding.tvBrandGr.text = product.brand
            binding.tvPrice.text = product.price
            binding.tvDescription.text = product.description


            binding.root.setOnClickListener { onClick(product) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val binding = ItemGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductVH(binding)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
