package com.raimzhanov.sweatapp.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.raimzhanov.sweatapp.R
import com.raimzhanov.sweatapp.databinding.ActivityForgotPasswordBinding
import com.raimzhanov.sweatapp.utils.Constants

class ForgotPasswordActivity : BaseActivity() {


    private  val viewBinding: ActivityForgotPasswordBinding by lazy {
        ActivityForgotPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setupActionbar()
    }

    private fun setupActionbar(){
           setSupportActionBar(viewBinding.toolbarForgotPasswordActivity)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            actionBar.title = Constants.EMPTY_VALUE
        }
        viewBinding.toolbarForgotPasswordActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        viewBinding.btnSubmit.setOnClickListener {

            val email:String = viewBinding.etEmailForgot.text.toString().trim(){it <= ' '}
            if (email.isEmpty()){
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
            }  else{
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                    hideProgressDialog()
                    if (it.isSuccessful){
                        Toast.makeText(this,resources.getString(R.string.email_sent_success),Toast.LENGTH_SHORT).show()
                         finish()
                    }
                    else{
                        showErrorSnackBar(it.exception!!.message.toString(),true)
                    }
                }
            }
        }
    }
}