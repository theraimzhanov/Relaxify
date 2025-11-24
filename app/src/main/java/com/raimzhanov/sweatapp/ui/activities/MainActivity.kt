package com.raimzhanov.sweatapp.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raimzhanov.sweatapp.databinding.ActivityMainBinding
import com.raimzhanov.sweatapp.utils.Constants

class MainActivity : AppCompatActivity() {

    private val viewBinding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val sharedPreferences = getSharedPreferences(Constants.SWEAT_PREFERENCES,Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,"")!!
         viewBinding.tvMain.text = "The logged in user is $userName"
    }
}