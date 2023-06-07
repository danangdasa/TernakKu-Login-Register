package com.dicoding.ternakku.data.retrofit


import com.dicoding.ternakku.data.retrofit.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("predict")
    fun predictDisease(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
    ) : Call<DiseaseResponse>

    @GET("details/{predictedClass}")
    fun getDetails(
        @Path("predictedClass") predictedClass: String
    ) : Call<DiseaseResponse>

    @GET("diseases")
    fun getList(
        @Header("Authorization") token: String,
    ) : Call<ListDiseasesResponse>

    @FormUrlEncoded
    @POST("authentication/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("authentication/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>
}