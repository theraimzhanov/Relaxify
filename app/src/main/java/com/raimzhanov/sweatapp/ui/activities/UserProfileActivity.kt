package com.raimzhanov.sweatapp.ui.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.raimzhanov.sweatapp.R
import com.raimzhanov.sweatapp.databinding.ActivityUserProfileBinding
import com.raimzhanov.sweatapp.firestore.FireStoreClass
import com.raimzhanov.sweatapp.models.User
import com.raimzhanov.sweatapp.utils.Constants
import com.raimzhanov.sweatapp.utils.GlideLoader
import java.io.IOException



class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private val viewBinding: ActivityUserProfileBinding by lazy {
        ActivityUserProfileBinding.inflate(layoutInflater)
    }

    private lateinit var userDetails: User
    private var selectedImageFileUri: Uri? = null
    private var userProfileImageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)



        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
            Log.d("TAG", "onCreate: ${userDetails.lastName}")

        }

        viewBinding.etFirstName.setText(userDetails.firstName)
        viewBinding.etLastName.setText(userDetails.lastName)
        viewBinding.etEmail.isEnabled = false
        viewBinding.etEmail.setText(userDetails.email)

        if (userDetails.profileCompleted ==0){
            viewBinding.tvTitle.text = resources.getString(R.string.title_complete_profile)
            viewBinding.etLastName.isEnabled = false
            viewBinding.etFirstName.isEnabled = false

        } else{
            setUpActionBar()
            viewBinding.tvTitle.text = resources.getString(R.string.title_edit_profile)
            GlideLoader(this).loadUserPicture(userDetails.image,viewBinding.ivUserPhoto)

            if (userDetails.mobile != 0L){
            viewBinding.etMobileNumber.setText(userDetails.mobile.toString())
        }
            if (userDetails.gender == Constants.MALE){
                viewBinding.rbMale.isChecked = true
            } else{
                viewBinding.rbFemale.isChecked = true
            }
        }

        viewBinding.ivUserPhoto.setOnClickListener(this)
        viewBinding.btnSubmit.setOnClickListener(this)
    }

    private fun setUpActionBar(){
        setSupportActionBar(viewBinding.toolbarUserProfileActivity)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_24)
        }
        viewBinding.toolbarUserProfileActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_user_photo -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {

                        Constants.showImageChooser(this)

                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }
                R.id.btn_submit -> {
                    if (validateUserProfileDetails()) {
                        showProgressDialog(resources.getString(R.string.please_wait))
                        if (selectedImageFileUri != null) {
                            FireStoreClass().uploadImageToCloudStorage(this, selectedImageFileUri)
                        } else {
                            updateUserProfileDetails()
                        }
                    }
                }
            }
        }
    }

    private fun updateUserProfileDetails() {
        val userHashMap = HashMap<String, Any>()

        val firstName = viewBinding.etFirstName.text.toString().trim(){ it<= ' '}
        if (firstName != userDetails.firstName){
            userHashMap[Constants.FIRST_NAME] = firstName
        }

        val lastName = viewBinding.etLastName.text.toString().trim(){ it<= ' '}
        if (lastName != userDetails.lastName){
            userHashMap[Constants.LAST_NAME] = lastName
        }


        val mobileNumber =
            viewBinding.etMobileNumber.text.toString().trim() { it <= ' ' }

        val gender = if (viewBinding.rbMale.isChecked) {
            Constants.MALE
        } else {
            Constants.FEMALE
        }
        if (userProfileImageUrl.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = userProfileImageUrl
        }

        if (mobileNumber.isNotEmpty() && mobileNumber != userDetails.mobile.toString()) {
            userHashMap[Constants.MOBILE] = mobileNumber.toLong()
        }

        if (gender.isNotEmpty() && gender != userDetails.gender){
            userHashMap[Constants.GENDER] = gender
        }

        userHashMap[Constants.COMPLETE_PROFILE] = 1

        FireStoreClass().updateUserProfileData(this, userHashMap)
    }

    fun userProfileUpdateSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this,
            resources.getString(R.string.msg_profile_update_success),
            Toast.LENGTH_SHORT
        )
            .show()
        startActivity(Intent(this, DashBoardActivity::class.java))
        finish()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //showErrorSnackBar("The storage permission is granted.",false)
                Constants.showImageChooser(this)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
            if (data != null) {
                try {
                    selectedImageFileUri = data.data!!
                    GlideLoader(this).loadUserPicture(
                        selectedImageFileUri!!,
                        viewBinding.ivUserPhoto
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(
                        this,
                        resources.getString(R.string.image_selection_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        /*  if (requestCode === Constants.PICK_IMAGE_REQUEST_CODE && resultCode === RESULT_OK && data!!.data != null) {

              // Get the Uri of data
              selectedImageFileUri = data.data
              try {

                  // Setting image on image view using Bitmap
                  val bitmap = MediaStore.Images.Media
                      .getBitmap(
                          contentResolver,
                          selectedImageFileUri
                      )
                  viewBinding.ivUserPhoto.setImageBitmap(bitmap)
              } catch (e: IOException) {
                  // Log the exception
                  e.printStackTrace()
              }
          }*/
    }

    private fun validateUserProfileDetails(): Boolean {
        return when {
            TextUtils.isEmpty(viewBinding.etMobileNumber.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), false)
                false
            }
            else -> {
                true
            }
        }
    }

    fun imageUpLoadSuccess(imageURL: String) {
        userProfileImageUrl = imageURL
        updateUserProfileDetails()
    }
}