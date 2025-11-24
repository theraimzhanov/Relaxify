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
import com.raimzhanov.sweatapp.ui.activities.SettingsActivity
import com.raimzhanov.sweatapp.ui.adapters.BannerAdapter
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
            Product(1, "Glow Face Cream", "For smooth glowing skin", "$12.99", R.drawable.pr4),
            Product(2, "Vitamin C Serum", "Brightens your skin", "$18.50", R.drawable.pr2),
            Product(3, "Aloe Vera Gel", "Hydration & cooling", "$9.99", R.drawable.pr3),
            Product(4, "Aloe Vera Gel", "Vitamin C & glowing", "$5.99", R.drawable.pr5),
            Product(5, "Aloe Vera Gel", "Brighter & cooling", "$14.00", R.drawable.pr7)
        )

        val adapter = ProductAdapter(products) { product ->
            Toast.makeText(requireContext(), "Clicked: ${product.name}", Toast.LENGTH_SHORT).show()
        }

        binding.rvBestSeller.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvBestSeller.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        isSliderActive = false

        sliderRunnable?.let { sliderHandler.removeCallbacks(it) }
        sliderRunnable = null

        _binding = null
    }
}

