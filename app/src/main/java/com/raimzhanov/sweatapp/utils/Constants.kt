package com.raimzhanov.sweatapp.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap


object Constants {
    const val USERS:String = "users"
    const val EMPTY_VALUE :String= ""
    const val SWEAT_PREFERENCES:String = "SweatPreference"
    const val LOGGED_IN_USERNAME:String = "logged_in_username"
    const val EXTRA_USER_DETAILS:String = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE:Int = 2
    const val PICK_IMAGE_REQUEST_CODE:Int = 1

    const val MALE:String = "Male"
    const val FEMALE:String = "Female"
    const val IMAGE:String = "image"
    const val COMPLETE_PROFILE:String = "profileCompleted"
    const val MOBILE:String = "mobile"
    const val GENDER:String = "gender"
    const val USER_PROFILE_IMAGE:String = "User_Profile_Image"
    const val FIRST_NAME:String = "firstName"
    const val LAST_NAME:String = "lastName"

    fun showImageChooser(activity:Activity){
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)

    }
    fun showImageChooserSecondWay(activity: Activity){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(Intent.createChooser(intent,"Select image from here"),
            PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity,uri: Uri?):String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver
            .getType(uri!!))
    }
}