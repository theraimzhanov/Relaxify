package com.raimzhanov.sweatapp.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.raimzhanov.sweatapp.ui.activities.LoginActivity
import com.raimzhanov.sweatapp.ui.activities.RegisterActivity
import com.raimzhanov.sweatapp.ui.activities.UserProfileActivity
import com.raimzhanov.sweatapp.models.User
import com.raimzhanov.sweatapp.ui.activities.SettingsActivity
import com.raimzhanov.sweatapp.utils.Constants
import kotlin.collections.HashMap

class FireStoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        mFireStore.collection((Constants.USERS))
            .document(userInfo.id).set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener {
                activity.hideProgressDialog()
            }
    }
    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = " "
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }
    fun getUserDetails(activity: Activity) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get().addOnSuccessListener {
                val user = it.toObject(User::class.java)!!

                val sharedPreferences = activity.getSharedPreferences(
                    Constants.SWEAT_PREFERENCES,
                    Context.MODE_PRIVATE
                )

                val editor:SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,"${user.firstName} ${user.lastName}"
                )
                editor.apply()

                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                    is SettingsActivity->{
                       activity.userDetailsSuccess(user)
                    }
                }
            }.addOnFailureListener {
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                    is SettingsActivity->{
                     activity.hideProgressDialog()
                    }
                }
            }
    }



    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String,Any>){
         mFireStore.collection(Constants.USERS).document(getCurrentUserID())
             .update(userHashMap).addOnSuccessListener {

                 when(activity){
                     is UserProfileActivity ->{
                         activity.userProfileUpdateSuccess()
                     }
                 }
             } .addOnFailureListener {
                 when(activity){
                     is UserProfileActivity ->{
                         activity.hideProgressDialog()
                     }
                 }
             }
     }

    fun uploadImageToCloudStorage(activity: Activity,imageFileUri:Uri?) {

         val storage :StorageReference = FirebaseStorage.getInstance().reference.child(
            Constants.USER_PROFILE_IMAGE+System.currentTimeMillis()+"."
        +Constants.getFileExtension(activity,imageFileUri)
        )
        storage.putFile(imageFileUri!!).addOnSuccessListener{ taskShapshot ->
            Log.d("firebase image uri", taskShapshot.metadata!!.reference!!.downloadUrl.toString())

            taskShapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {uri->
                Log.d("downloadable image uri", uri.toString())
                    when(activity){
                        is UserProfileActivity ->{
                            activity.imageUpLoadSuccess(uri.toString())
                        }
                    }
            }
        }.addOnFailureListener{exception->
            when(activity){
                is UserProfileActivity ->{
                    activity.hideProgressDialog()
                    Toast.makeText(activity.applicationContext,exception.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }

     /*   if (imageFileUri != null){
            val ref: StorageReference = FirebaseStorage.getInstance().reference
                .child(
                    "images/"
                            + UUID.randomUUID().toString()
                )
            ref.putFile(imageFileUri).addOnSuccessListener {
                when(activity){
                    is UserProfileActivity->{
                        activity.hideProgressDialog()
                        Toast.makeText(activity.applicationContext,"Successfully",Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener{
                when(activity){
                    is UserProfileActivity->{
                        activity.hideProgressDialog()
                        Toast.makeText(activity.applicationContext,it.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }*/
    }
}