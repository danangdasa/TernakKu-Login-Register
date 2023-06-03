package com.dicoding.ternakku.data.retrofit


import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.Call
import retrofit2.http.Query

interface ApiService {

    @Multipart
    @POST("predict")
    fun predictDisease(
        @Part image: MultipartBody.Part,
    ) : Call<DiseaseResponse>

    @GET("details/{predictedClass}")
    fun getDetails(
        @Path("predictedClass") predictedClass: String
    ) : Call<DiseaseResponse>

    @GET("predict")
    fun getImg(
        @Query ("image_name") imageName: String,
    ) : Call<DiseaseResponse>
}