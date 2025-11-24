package com.raimzhanov.sweatapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.raimzhanov.sweatapp.R
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadUserPicture(imageURI:Any,imageView:ImageView){
        try {
            Glide.with(context)
                .load(imageURI)
                .centerCrop()
                .placeholder(R.drawable.no_idea)
                .into(imageView)
        } catch (e:IOException){
            e.printStackTrace()
        }
    }
}