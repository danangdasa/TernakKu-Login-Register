package com.dicoding.ternakku.data.retrofit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disease (
    val diseaseName : String,
    val diseaseDetails : String,
    val handlingMethod : String,
): Parcelable