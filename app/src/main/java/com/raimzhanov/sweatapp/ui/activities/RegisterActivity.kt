package com.raimzhanov.sweatapp.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.raimzhanov.sweatapp.R
import com.raimzhanov.sweatapp.databinding.ActivityRegisterBinding
import com.raimzhanov.sweatapp.firestore.FireStoreClass
import com.raimzhanov.sweatapp.models.User

class RegisterActivity : BaseActivity() {

private val viewBinding : ActivityRegisterBinding by lazy {
    ActivityRegisterBinding.inflate(layoutInflater)
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.tvLogin.setOnClickListener {
            onBackPressed()
        }
        setupActionBar()
        viewBinding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(viewBinding.toolbarRegisterActivity)
        val actionBar  = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        viewBinding.toolbarRegisterActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    private fun validateRegisterDetails():Boolean{
        return when{
            TextUtils.isEmpty(viewBinding.etFirstName.text.toString().trim(){it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name),true)
                false
            }
            TextUtils.isEmpty(viewBinding.etLastName.text.toString().trim(){it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name),true)
                false
            }
            TextUtils.isEmpty(viewBinding.etEmail.text.toString().trim(){it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }
            TextUtils.isEmpty(viewBinding.etPassword.text.toString().trim(){it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }
            TextUtils.isEmpty(viewBinding.etConfirmPassword.text.toString().trim(){it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password),true)
                false
            }
            viewBinding.etPassword.text.toString().trim(){ it <= ' '} !=viewBinding.etConfirmPassword.text.toString()
                .trim { it <= ' ' } ->{
                    showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),true)
                false
                }
            !viewBinding.cbTermsAndCondition.isChecked->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_agree_terms_and_condition),true)
                false
            } else->{
               // showErrorSnackBar(resources.getString(R.string.register_successful),false)
                true
            }
        }
    }
    private fun  registerUser(){
        if (validateRegisterDetails()){

            showProgressDialog(resources.getString(R.string.please_wait))

            val email:String = viewBinding.etEmail.text.toString().trim(){ it<= ' '}
            val password:String = viewBinding.etPassword.text.toString().trim(){ it<= ' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        hideProgressDialog()
                        val firebaseUser: FirebaseUser = it.result!!.user!!

                        val user = User(firebaseUser.uid,
                        viewBinding.etFirstName.text.toString().trim(){it<=' '},
                        viewBinding.etLastName.text.toString().trim(){it<=' '},
                        viewBinding.etEmail.text.toString().trim(){it<=' '},
                            )

                        FireStoreClass().registerUser(this,user)

                        FirebaseAuth.getInstance().signOut()
                        finish()

                    } else{
                        showErrorSnackBar(it.exception!!.message.toString(),true)
                    }
                }
        }
    }

    fun userRegistrationSuccess(){
       hideProgressDialog()
       Toast.makeText(this,resources.getString(R.string.register_successful),Toast.LENGTH_SHORT).show()

    }

}