package com.dicoding.ternakku.data.retrofit.response

data class ListResponse(
	val listResponse: List<ListResponseItem?>? = null
)

data class ListResponseItem(
	val diseaseDetails: String? = null,
	val handlingMethod: String? = null,
	val diseaseName: String? = null
)

