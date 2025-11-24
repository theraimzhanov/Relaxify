package com.raimzhanov.sweatapp.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.raimzhanov.sweatapp.R



open class BaseActivity : AppCompatActivity() {

private lateinit var mProgressDialog:Dialog
private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
    fun showErrorSnackBar(message: String,errorMessage: Boolean){
        val snackBar = Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage){
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this,R.color.colorSnackBarError
                )
            )
        }   else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this,R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }
    fun showProgressDialog(text:String){
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.diolog_progress)
        mProgressDialog.setTitle(text)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    fun doubleBackToExit(){
        if (doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this,resources.getString(R.string.please_click_back_again_to_exit),Toast.LENGTH_SHORT)
            .show()
        Handler(Looper.getMainLooper()).postDelayed({doubleBackToExitPressedOnce = false},2000)
    }
}