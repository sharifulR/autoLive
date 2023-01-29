package com.example.autolive.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autolive.models.ProfileImageRequest
import com.example.autolive.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.http.Part
import javax.inject.Inject

@HiltViewModel
class ProfileImageViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val profileImageLiveData get() = userRepository.userResponseLiveData
    fun uploadProfileImage(avatar: MultipartBody.Part){
        viewModelScope.launch {
            userRepository.uploadProfileImage(avatar)
        }
    }
}