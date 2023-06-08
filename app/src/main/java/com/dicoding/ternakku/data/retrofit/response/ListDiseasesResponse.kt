package com.dicoding.ternakku.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class ListDiseasesResponse(

	@field:SerializedName("diseases")
	val diseases: ArrayList<ListDiseasesResponseItem>
)

data class ListDiseasesResponseItem(

	@field:SerializedName("disease_details")
	val diseaseDetails: String,

	@field:SerializedName("handling_method")
	val handlingMethod: String,

	@field:SerializedName("disease_name")
	val diseaseName: String
)
