package com.raimzhanov.sweatapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.raimzhanov.sweatapp.R
import com.raimzhanov.sweatapp.databinding.ActivitySuccessActivtyBinding
import com.raimzhanov.sweatapp.ui.fragments.OrdersFragment
import java.util.logging.Handler

class SuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessActivtyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Wait 2 seconds → Go to Orders screen
        android.os.Handler().postDelayed({
            startActivity(Intent(this, DashBoardActivity::class.java))
            finish()
        }, 3000)
    }
}