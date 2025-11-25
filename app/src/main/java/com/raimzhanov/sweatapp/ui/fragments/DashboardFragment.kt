package com.raimzhanov.sweatapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.raimzhanov.sweatapp.R
import com.raimzhanov.sweatapp.databinding.FragmentDashboardBinding
import com.raimzhanov.sweatapp.model.Product
import com.raimzhanov.sweatapp.models.Brand
import com.raimzhanov.sweatapp.ui.activities.PaymentActivity
import com.raimzhanov.sweatapp.ui.activities.SettingsActivity
import com.raimzhanov.sweatapp.ui.adapters.BannerAdapter
import com.raimzhanov.sweatapp.ui.adapters.BrandAdapter
import com.raimzhanov.sweatapp.ui.adapters.ProductAdapter
import kotlin.jvm.java
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val sliderHandler = Handler(Looper.getMainLooper())
    private var sliderRunnable: Runnable? = null
    private var isSliderActive = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBannerSlider()
        setupRecyclerView()
        setupPopularBrands()


        binding.ivUser.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
    }

    private fun setupBannerSlider() {

        val banners = listOf(
            R.drawable.banner_1,
            R.drawable.banner_2,
            R.drawable.banner_3
        )

        val adapter = BannerAdapter(banners)
        binding.bannerViewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.bannerViewPager)

        isSliderActive = true

        sliderRunnable = object : Runnable {
            override fun run() {
                if (_binding == null || !isSliderActive) return

                val current = binding.bannerViewPager.currentItem
                val next = if (current == banners.size - 1) 0 else current + 1
                binding.bannerViewPager.currentItem = next

                sliderHandler.postDelayed(this, 3000)
            }
        }

        sliderHandler.postDelayed(sliderRunnable!!, 3000)
    }

    private fun setupRecyclerView() {

        val products = listOf(
            Product(1, "Glow Face Cream", "For smooth glowing skin", "$12.99", "Lumiéra",R.drawable.pr4),
            Product(2, "Vitamin C Serum", "Brightens your skin", "$18.50", "PureLyn",R.drawable.pr2),
            Product(3, "Aloe Vera Gel", "Hydration & cooling", "$9.99", "Aveline",R.drawable.pr3),
            Product(4, "Sun Vera Full", "Vitamin C & glowing", "$5.99", "NovaSkin",R.drawable.pr5),
            Product(5, "Serie Fifa Cell", "Brighter & cooling", "$14.00", "Seraphique",R.drawable.pr7)
        )

        val adapter = ProductAdapter(products) { product ->
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            intent.putExtra("id", product.id)
            intent.putExtra("name", product.name)
            intent.putExtra("brand", product.brand)
            intent.putExtra("description", product.description)
            intent.putExtra("price", product.price)
            intent.putExtra("image", product.image)
            startActivity(intent)
        }

        binding.rvBestSeller.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvBestSeller.adapter = adapter
    }

    private fun setupPopularBrands() {

        val brands = listOf(
            Brand(1, "Lumiéra", R.drawable.br1),
            Brand(2, "PureLyn", R.drawable.br2),
            Brand(3, "Elara Bloom", R.drawable.br56),
            Brand(4, "Seraphique", R.drawable.br57),
            Brand(5, "Veloria", R.drawable.br45),
            Brand(6, "Celestique", R.drawable.br6),
        )

        val adapter = BrandAdapter(brands) {
            Toast.makeText(requireContext(), "Selected: ${it.name}", Toast.LENGTH_SHORT).show()
        }

        binding.rvPopularBrands.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvPopularBrands.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()

        isSliderActive = false

        sliderRunnable?.let { sliderHandler.removeCallbacks(it) }
        sliderRunnable = null

        _binding = null
    }
}

