package com.example.mobileproject.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
class User (
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val mobile: Long = 0,
    val gender: String = "",
    val address1: String = "",
    val address2: String = "",
    val address3: String = "",
    val profileCompleted: Int=0
): Parcelable