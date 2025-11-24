package com.raimzhanov.sweatapp.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MSPTextViewBold(context: Context,attrs:AttributeSet):AppCompatTextView(context,attrs) {

init {
    appleFont()
}

     private fun appleFont() {
      val typeface:Typeface = Typeface.createFromAsset(context.assets,"montserrat_bold.ttf")
         setTypeface(typeface)
    }
}
