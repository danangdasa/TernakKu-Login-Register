package com.dicoding.ternakku.data.retrofit.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiseaseResponse(

	@field:SerializedName("image_name")
	val imageName: String,

	@field:SerializedName("disease_details")
	val diseaseDetails: String,

	@field:SerializedName("handling_method")
	val handlingMethod: String,

	@field:SerializedName("original_image")
	val originalImage: String,

	@field:SerializedName("disease_name")
	val diseaseName: String
) : Parcelable
