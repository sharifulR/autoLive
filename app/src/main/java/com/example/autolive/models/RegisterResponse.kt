package com.example.autolive.models


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String
) {
    data class Data(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("mobile")
        val mobile: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("status")
        val status: Boolean,
        @SerializedName("token")
        val token: String,
        @SerializedName("updated_at")
        val updatedAt: String
    )
}