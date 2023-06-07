package com.dicoding.ternakku.data.retrofit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disease(
    val id : Int,
    val name : String,
    val detail : String,
    val heandle : String
) : Parcelable