package com.raimzhanov.sweatapp.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MSPEditText(context: Context,attributes: AttributeSet):AppCompatEditText(context,attributes) {
init {
    applyFont()
}

private fun applyFont(){
    val typeFace:Typeface = Typeface.createFromAsset(context.assets,"montserrat_reqular.ttf")
    setTypeface(typeFace)
}
}