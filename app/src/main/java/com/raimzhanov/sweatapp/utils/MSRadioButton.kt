package com.raimzhanov.sweatapp.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class MSRadioButton(context: Context,attributeSet: AttributeSet): AppCompatRadioButton(context,attributeSet) {
    init {
        applyFont()
    }
    private fun applyFont(){

        val typeFace = Typeface.createFromAsset(context.assets,"montserrat_bold.ttf")
        typeface = typeFace
    }
}