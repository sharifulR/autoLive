package com.example.autolive.viewModel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autolive.models.LoginRequest
import com.example.autolive.models.LoginResponse
import com.example.autolive.models.RegisterRequest
import com.example.autolive.models.RegisterResponse
import com.example.autolive.repository.UserRepository
import com.example.autolive.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel(){

    //signup
    val userResponseLiveData : LiveData<NetworkResult<RegisterResponse>>
    get() = userRepository.userResponseLiveData

    //login
    val userLoginResponseLiveData : LiveData<NetworkResult<LoginResponse>>
    get() = userRepository.userLoginResponseLiveData


    fun signup(registerRequest:RegisterRequest){
        viewModelScope.launch {
            userRepository.signup(registerRequest)
        }
    }

    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            userRepository.login(loginRequest)
        }

    }

    fun validateCredentials(name: String,mobile:String,password:String,confirmPassword:String):Pair<Boolean,String>{
        var result=Pair(true,"")
        if (TextUtils.isEmpty(name)|| TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
            result=Pair(false,"")
        }
        else if (password.length<=7){
            result=Pair(false,"password must be 8 digit")
        }
        else if (!confirmPassword.equals(password)){
            result=Pair(false,"password must be same")
        }

        return result

    }
    fun validateLoginCredentials(mobile:String,password:String):Pair<Boolean,String>{
        var result=Pair(true,"")
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password)){
            result=Pair(false,"")
        }
        else if (password.length<=7){
            result=Pair(false,"password must be 8 digit")
        }

        return result

    }
}