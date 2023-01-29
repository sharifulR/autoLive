package com.example.autolive.api

import com.example.autolive.models.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST("register")
    suspend fun signup(@Body registerRequest: RegisterRequest):Response<RegisterResponse>
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest):Response<LoginResponse>

    /*@GET("user")
    fun getUserInfo(userId: String):Response<LoginResponse>*/

    @FormUrlEncoded
    @POST("user")
    fun userPersonalData(@Field("id") id: String?): Call<LoginResponse?>?

    /*@PUT("user/{userId}")
    suspend fun updateUser(@Path("userId") userId: String, @Body )*/

    @DELETE("user/{userId}")
    suspend fun deleteAccount(@Path("userId") userId:String):Response<LoginResponse>

    /*@Multipart
    @POST("user/avatar")
    suspend fun imageUpload(@Part avatar:MultipartBody.Part): Response<LoginResponse>*/

    //@Part MultipartBody.Part  file
    //16
    //@Part MultipartBody.Part file
    @Multipart
    @POST("user/avatar")
    suspend fun imageUploadFile(@Part avatar: MultipartBody.Part): Response<LoginResponse>


}