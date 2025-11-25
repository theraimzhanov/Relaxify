package com.raimzhanov.sweatapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raimzhanov.sweatapp.databinding.ItemBrandBinding
import com.raimzhanov.sweatapp.models.Brand

class BrandAdapter(
    private val list: List<Brand>,
    private val onClick: (Brand) -> Unit
) : RecyclerView.Adapter<BrandAdapter.BrandVH>() {

    inner class BrandVH(val binding: ItemBrandBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Brand) {
            binding.ivBrand.setImageResource(item.image)
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandVH {
        val binding = ItemBrandBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BrandVH(binding)
    }

    override fun onBindViewHolder(holder: BrandVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
