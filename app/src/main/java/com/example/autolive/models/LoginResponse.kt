package com.example.autolive.models


import com.google.gson.annotations.SerializedName
import java.io.File

data class LoginResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String
) {
    data class Data(
        @SerializedName("apps_id")
        val appsId: Any,
        @SerializedName("avatar")
        val avatar: String,
        @SerializedName("balance")
        val balance: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("device_id")
        val deviceId: Any,
        @SerializedName("diamond")
        val diamond: Int,
        @SerializedName("email")
        val email: Any,
        @SerializedName("email_verified_at")
        val emailVerifiedAt: Any,
        @SerializedName("id")
        val id: Int,
        @SerializedName("is_admin")
        val isAdmin: Int,
        @SerializedName("is_user")
        val isUser: Int,
        @SerializedName("mobile")
        val mobile: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("otp_verified_at")
        val otpVerifiedAt: Any,
        @SerializedName("status")
        val status: Int,
        @SerializedName("token")
        val token: String,
        @SerializedName("updated_at")
        val updatedAt: String
    )
}