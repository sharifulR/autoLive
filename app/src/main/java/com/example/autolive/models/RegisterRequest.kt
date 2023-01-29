package com.example.autolive.models

data class RegisterRequest(
    val name:String,
    val mobile:String,
    val password:String,
    val password_confirmation:String,
)
