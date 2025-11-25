package com.raimzhanov.sweatapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.raimzhanov.sweatapp.R
import com.raimzhanov.sweatapp.databinding.ActivityPaymentBinding
import kotlin.jvm.java

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private var quantity = 1
    private var priceValue = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive data
        val name = intent.getStringExtra("name")
        val brand = intent.getStringExtra("brand")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")?.replace("$", "")
        val image = intent.getIntExtra("image", 0)

        // Convert price to double
        priceValue = price?.toDoubleOrNull() ?: 0.0

        // Set initial UI
        binding.tvProductName.text = name
        binding.tvBrand.text = brand
        binding.tvDescription.text = description
        binding.tvAmount.text = "$$priceValue"
        binding.tvTotal.text = "Total: $$priceValue"
        Glide.with(this).load(image).into(binding.ivProduct)

        // Quantity buttons
        binding.btnPlus.setOnClickListener {
            quantity++
            updateTotal()
        }

        binding.btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateTotal()
            }
        }

        // Payment button
        binding.btnPayNow.setOnClickListener {
            if (validatePayment()) {
                startActivity(Intent(this, SuccessActivity::class.java))
            }
            Toast.makeText(this, "Payment unsuccessful, please try again.", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateTotal() {
        binding.tvQuantity.text = quantity.toString()
        val total = priceValue * quantity
        binding.tvTotal.text = "Total: $${String.format("%.2f", total)}"
    }

    private fun validatePayment(): Boolean {
        if (binding.etCardNumber.text.isNullOrEmpty()) {
            binding.etCardNumber.error = "Enter card number"
            return false
        }
        if (binding.etExpiry.text.isNullOrEmpty()) {
            binding.etExpiry.error = "MM/YY"
            return false
        }
        if (binding.etCvv.text.isNullOrEmpty()) {
            binding.etCvv.error = "CVV"
            return false
        }
        if (binding.etCardName.text.isNullOrEmpty()) {
            binding.etCardName.error = "Name required"
            return false
        }
        return true
    }
}