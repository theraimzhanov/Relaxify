package com.raimzhanov.sweatapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raimzhanov.sweatapp.databinding.ItemTopCosmeticBinding
import com.raimzhanov.sweatapp.model.Product


class CosmeticAdapter(
    private val cosmeticList: List<Product>,
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<CosmeticAdapter.CosmeticViewHolder>() {

    inner class CosmeticViewHolder(val binding: ItemTopCosmeticBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.ivCosmetic.setImageResource(item.image)

            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CosmeticViewHolder {
        val binding = ItemTopCosmeticBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CosmeticViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CosmeticViewHolder, position: Int) {
        holder.bind(cosmeticList[position])
    }

    override fun getItemCount(): Int = cosmeticList.size
}
