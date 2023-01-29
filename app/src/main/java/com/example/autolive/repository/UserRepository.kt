package com.example.autolive.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.autolive.api.UserApi
import com.example.autolive.models.*
import com.example.autolive.utils.NetworkResult
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Part
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    //Register
    private val _userResponseLiveData=MutableLiveData<NetworkResult<RegisterResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<RegisterResponse>>
    get() = _userResponseLiveData

    suspend fun signup(registerRequest: RegisterRequest){
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response=userApi.signup(registerRequest)
        handleResponse(response)
    }
    private fun handleResponse(response: Response<RegisterResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

//login
    private val _userLoginResponseLiveData=MutableLiveData<NetworkResult<LoginResponse>>()
    val userLoginResponseLiveData: LiveData<NetworkResult<LoginResponse>>
    get() = _userLoginResponseLiveData

    suspend fun login(loginRequest: LoginRequest){
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response=userApi.login(loginRequest)
        handleLoginResponse(response)
    }
    private fun handleLoginResponse(response: Response<LoginResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userLoginResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userLoginResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userLoginResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    //uploadProfileImage
    private val _imageUploadResponse=MutableLiveData<NetworkResult<LoginResponse>>()
    val imageUploadResponse: LiveData<NetworkResult<LoginResponse>>
        get() = _imageUploadResponse
    suspend fun uploadProfileImage(avatar: MultipartBody.Part){
        val response=userApi.imageUploadFile(avatar)

        if (response.isSuccessful && response.body() != null) {
            _imageUploadResponse.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _imageUploadResponse.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _imageUploadResponse.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}