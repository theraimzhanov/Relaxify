package com.raimzhanov.sweatapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.raimzhanov.sweatapp.R
import com.raimzhanov.sweatapp.databinding.FragmentProductsBinding
import com.raimzhanov.sweatapp.model.Product
import com.raimzhanov.sweatapp.ui.activities.PaymentActivity
import com.raimzhanov.sweatapp.ui.adapters.CosmeticAdapter
import com.raimzhanov.sweatapp.ui.adapters.ProductGridAdapter

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductsList()
        setupTopCosmetics()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setupProductsList() {

        val productList = listOf(

            Product(20, "Hydra Fresh Serum", "Deep hydration for dry skin", "$19.99", "Lumiéra", R.drawable.t2),
            Product(21, "Glow Boost Cream", "Instant brightening cream", "$14.50", "PureLyn", R.drawable.it2),
            Product(22, "Aloe Cooling Gel", "Soothes irritated skin", "$9.99", "Elara Bloom", R.drawable.t3),
            Product(23, "Night Repair Cream", "Anti-aging overnight repair", "$24.99", "Seraphique", R.drawable.it4),
            Product(24, "Velvet Moisture Lotion", "Soft & smooth daily lotion", "$16.99", "Veloria", R.drawable.t2),

            Product(25, "Vitamin C Glow Serum", "Brightens and evens tone", "$18.75", "Celestique", R.drawable.t1),
            Product(26, "Rose Water Toner", "Hydration & pore tightening", "$11.20", "Aurelya", R.drawable.it1),
            Product(27, "Silk Touch Hand Cream", "Ultra-smooth silky hands", "$7.99", "Mirabelle", R.drawable.it3),
            Product(28, "Charcoal Detox Mask", "Deep pore cleansing", "$12.40", "PureLyn", R.drawable.t5),
            Product(29, "Marine Collagen Cream", "Reduces fine lines", "$22.90", "Lumiéra", R.drawable.br1),

            Product(30, "Peptide Lift Serum", "Firming anti-aging formula", "$29.99", "Seraphique", R.drawable.t1),
            Product(31, "Aloe + Mint Mist", "Refreshing face spray", "$6.99", "Elara Bloom", R.drawable.t4),
            Product(32, "Honey Glow Moisturizer", "Nourishing honey-based cream", "$13.99", "Veloria", R.drawable.br1),
            Product(33, "Hydra Silk Sunscreen", "SPF 50 lightweight sun care", "$17.45", "Lumiéra", R.drawable.t8),
            Product(34, "Vitamin E Repair Oil", "Skin-restoring oil blend", "$15.50", "Celestique", R.drawable.it4),

            Product(35, "Lavender Sleep Mask", "Relaxing nighttime mask", "$10.99", "Aurelya", R.drawable.t6),
            Product(36, "Green Tea Pore Serum", "Calming anti-redness serum", "$12.80", "PureLyn", R.drawable.t7),
            Product(37, "Ice Crystal Face Wash", "Cooling foam cleanser", "$8.99", "Mirabelle", R.drawable.it2),
            Product(38, "HydraSmooth Gel", "Cooling hydration gel", "$11.60", "Veloria", R.drawable.t2),
            Product(39, "BrightGlow Clay Mask", "Deep exfoliating clay mask", "$14.10", "Elara Bloom", R.drawable.t8),

            Product(40, "Golden Radiance Oil", "Luxury gold-infused skin oil", "$27.50", "Seraphique", R.drawable.t3),
            Product(41, "Pure Aloe Essence", "100% natural soothing gel", "$7.50", "Aurelya", R.drawable.it1),
            Product(42, "HydroBerry Cream", "Berry-extract hydration", "$13.70", "Mirabelle", R.drawable.t8),
            Product(43, "Crystal Dew Toner", "Refreshing facial water", "$9.40", "Celestique", R.drawable.t2),
            Product(44, "SoftBloom Moisture Gel", "Lightweight moisturizing gel", "$12.55", "Lumiéra", R.drawable.t3)
        )


        val adapter = ProductGridAdapter(productList) {
                product ->
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            intent.putExtra("id", product.id)
            intent.putExtra("name", product.name)
            intent.putExtra("brand", product.brand)
            intent.putExtra("description", product.description)
            intent.putExtra("price", product.price)
            intent.putExtra("image", product.image)
            startActivity(intent)
        }

        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.adapter = adapter
    }

    private fun setupTopCosmetics() {

        val topCosmetics = listOf(

            Product(
                11,
                "Lumiéra Radiance",
                "Top cosmetic line for glowing skin",
                "-",
                "Lumiéra",
                R.drawable.t1
            ),

            Product(
                12,
                "PureLyn Organic",
                "Organic cosmetic essentials",
                "-",
                "PureLyn",
                R.drawable.t2
            ),

            Product(
                13,
                "Elara Bloom Care",
                "Smooth and hydrating cosmetics",
                "-",
                "Elara Bloom",
                R.drawable.t3
            ),

            Product(
                14,
                "Seraphique Beauty",
                "Premium cosmetic formulations",
                "-",
                "Seraphique",
                R.drawable.t4
            ),

            Product(
                15,
                "Veloria SilkSkin",
                "Gentle & soft skincare products",
                "-",
                "Veloria",
                R.drawable.t5
            ),

            Product(
                16,
                "Celestique Glow",
                "Advanced brightening cosmetics",
                "-",
                "Celestique",
                R.drawable.t6
            ),

            Product(
                17,
                "Aurelya SkinLab",
                "Luxury anti-aging skincare",
                "-",
                "Aurelya",
                R.drawable.t7
            ),

            Product(
                18,
                "Mirabelle Glow Co.",
                "Natural beauty & hydration line",
                "-",
                "Mirabelle",
                R.drawable.t8
            )
        )


        val adapter = CosmeticAdapter(topCosmetics) { item ->
            Toast.makeText(requireContext(), "Selected: ${item.name}", Toast.LENGTH_SHORT).show()
        }

        binding.rvCosmetics.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvCosmetics.adapter = adapter
    }
}