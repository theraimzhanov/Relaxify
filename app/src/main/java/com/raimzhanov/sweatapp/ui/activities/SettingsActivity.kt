package com.raimzhanov.sweatapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.raimzhanov.sweatapp.R
import com.raimzhanov.sweatapp.databinding.ActivitySettingsBinding
import com.raimzhanov.sweatapp.firestore.FireStoreClass
import com.raimzhanov.sweatapp.models.User
import com.raimzhanov.sweatapp.utils.Constants
import com.raimzhanov.sweatapp.utils.GlideLoader

class SettingsActivity : BaseActivity(),View.OnClickListener {

    private val viewBinding:ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }
    private lateinit var mUserDetails:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
     setUpActionBar()

        viewBinding.btnLogout.setOnClickListener(this)
        viewBinding.tvEdit.setOnClickListener(this)
    }

    private fun setUpActionBar(){
        setSupportActionBar(viewBinding.toolbarSettingsActivity)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_24)
        }
        viewBinding.toolbarSettingsActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getUserDetails(){
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().getUserDetails(this)
    }

    fun userDetailsSuccess(user: User){

mUserDetails = user
        hideProgressDialog()
        GlideLoader(this).loadUserPicture(user.image,viewBinding.ivUserPhoto)
        Log.d("TAG", "userDetailsSuccess: ")
        viewBinding.tvName.text = "${user.firstName} ${user.lastName}"
        viewBinding.tvGender.text = user.gender
        viewBinding.tvEmail.text = user.email
        viewBinding.tvMobileNumber.text = user.mobile.toString()
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    override fun onClick(v: View?) {
        if (v!= null){
            when(v.id) {
                R.id.btn_logout-> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this,LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                R.id.tv_edit ->{
                    val intent = Intent(this,UserProfileActivity::class.java)
                    intent.putExtra(Constants.EXTRA_USER_DETAILS,mUserDetails)
                    startActivity(intent)
                }
            }
        }
    }
}