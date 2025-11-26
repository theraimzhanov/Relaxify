package com.raimzhanov.sweatapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.raimzhanov.sweatapp.R
import com.raimzhanov.sweatapp.data.local.AppDatabase
import com.raimzhanov.sweatapp.data.local.Order
import com.raimzhanov.sweatapp.data.local.OrderRepository
import com.raimzhanov.sweatapp.databinding.ActivityPaymentBinding
import com.raimzhanov.sweatapp.ui.viewModel.OrderVMFactory
import com.raimzhanov.sweatapp.ui.viewModel.OrderViewModel
import kotlin.jvm.java

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var orderViewModel: OrderViewModel
    private var quantity = 1
    private var priceValue = 0.0
    private var productImage = 0
    private var finalTotal = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dao = AppDatabase.getDatabase(this).orderDao()
        val repo = OrderRepository(dao)
        orderViewModel =
            ViewModelProvider(this, OrderVMFactory(repo))[OrderViewModel::class.java]

        val name = intent.getStringExtra("name")
        val brand = intent.getStringExtra("brand")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")?.replace("$", "")
         productImage = intent.getIntExtra("image", 0)


        priceValue = price?.toDoubleOrNull() ?: 0.0

        // Set initial UI
        binding.tvProductName.text = name
        binding.tvBrand.text = brand
        binding.tvDescription.text = description
        binding.tvAmount.text = "$$priceValue"
        binding.tvTotal.text = "Total: $$priceValue"
        Glide.with(this).load(productImage).into(binding.ivProduct)
             updateTotal()

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

        binding.btnPayNow.setOnClickListener {
            if (validatePayment()) {

                val order = Order(
                    orderNumber = generateOrderNumber(),
                    productName = name.toString(),
                    productImage = productImage,
                    quantity = quantity,
                    totalAmount = finalTotal,
                    shippingAddress = "12250 S Kirkwood, Stafford, TX",
                    orderStatus = "Confirmed",
                    date = System.currentTimeMillis()
                )

                orderViewModel.saveOrder(order)

                startActivity(Intent(this, SuccessActivity::class.java))
                finish()
                return@setOnClickListener
            }


            Toast.makeText(this, "Payment unsuccessful. Try again!", Toast.LENGTH_LONG).show()
        }
    }
    fun generateOrderNumber(): String {
        val number = (10000..100000).random()
        return number.toString()
    }
    private fun updateTotal() {
        binding.tvQuantity.text = quantity.toString()
        finalTotal = priceValue * quantity
        binding.tvTotal.text = "Total: $${String.format("%.2f", finalTotal)}"
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