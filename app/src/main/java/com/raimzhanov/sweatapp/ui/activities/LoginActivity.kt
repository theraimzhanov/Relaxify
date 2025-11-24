package com.raimzhanov.sweatapp.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.raimzhanov.sweatapp.R
import com.raimzhanov.sweatapp.databinding.ActivityLoginBinding
import com.raimzhanov.sweatapp.models.User
import com.raimzhanov.sweatapp.utils.Constants


class LoginActivity : BaseActivity(), View.OnClickListener {

    private val viewBinding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        checkUser()
         startAnimation()
        viewBinding.tvForgotPassword.setOnClickListener(this)
        viewBinding.btnLogin.setOnClickListener(this)
        viewBinding.tvRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_forgot_password -> {
                    startActivity(Intent(this, ForgotPasswordActivity::class.java))
                }
                R.id.btn_login -> {
                    Log.d("TAG", "Attempting sign-in for")

                    loginRegisteredUser()
                }
                R.id.tv_register -> {
                    startActivity(Intent(this, RegisterActivity::class.java))
                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(viewBinding.etEmail.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }
            TextUtils.isEmpty(viewBinding.etPassword.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun loginRegisteredUser() {
        if (validateLoginDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))

            val email = viewBinding.etEmail.text.toString().trim() { it <= ' ' }
            val password = viewBinding.etPassword.text.toString().trim() { it <= ' ' }
            Log.d("TAG", "Attempting sign-in for $email")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                    if (it.isSuccessful) {

                     //  FireStoreClass().getUserDetails(this)
                        startActivity(Intent(this, DashBoardActivity::class.java))
                        finish()

                    } else {
                        Log.d("TAG", "Attempting sign-in for errort")

                        showErrorSnackBar(it.exception!!.message.toString(), true)
                        hideProgressDialog()
                    }
                }
        }
    }


    fun userLoggedInSuccess(user:User){
        hideProgressDialog()

        if(user.profileCompleted == 0){
            val intent = Intent(this, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS,user)
            startActivity(intent)
        }
        else{
            startActivity(Intent(this, DashBoardActivity::class.java))
        }
        finish()
    }




    private fun startAnimation(){
        viewBinding.tvWelcome.startAnimation(AnimationUtils.loadAnimation(this,R.anim.top_wave))
    }
    @SuppressLint("SuspiciousIndentation")
    private fun checkUser(){
        val mAuth = FirebaseAuth.getInstance()
    val user:FirebaseUser? = mAuth.currentUser
        if (user != null){
            startActivity(Intent(this,DashBoardActivity::class.java))
            finish()
    }
    }

}