package com.raimzhanov.sweatapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize   // ✅ updated import (modern, correct one)

@Parcelize
class User(
    val id: String = EMPTY_VALUE,
    val firstName: String = EMPTY_VALUE,
    val lastName: String = EMPTY_VALUE,
    val email: String = EMPTY_VALUE,
    val image: String = EMPTY_VALUE,
    val mobile: Long = START_VALUE_LONG,
    val gender: String = EMPTY_VALUE,
    val profileCompleted: Int = START_VALUE_INT
) : Parcelable {

    companion object {
        private const val EMPTY_VALUE = ""
        private const val START_VALUE_LONG: Long = 0
        private const val START_VALUE_INT: Int = 0
    }
}
