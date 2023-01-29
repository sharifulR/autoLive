package com.example.autolive.activity

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.autolive.api.UserApi
import com.example.autolive.databinding.ActivityEditProfileBinding
import com.example.autolive.utils.NetworkResult
import com.example.autolive.utils.URIPathHelper
import com.example.autolive.viewModel.ProfileImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    lateinit var selectedImageUri: Uri
    private var bitmap: Bitmap? = null
    private lateinit var part: MultipartBody.Part

    private val viewModel  by viewModels<ProfileImageViewModel>()

    private val uploadImage=registerForActivityResult(ActivityResultContracts.GetContent()){
        selectedImageUri=it!!
        binding.profilePictureProfile.setImageURI(it)

            uploadPImage(it)
    }

    private fun uploadPImage(imageUri: Uri) {
        val uriPathHelper = URIPathHelper()
        val filePath = uriPathHelper.getPath(this, imageUri/*selectedImageUri!!*/)
        Log.i("FilePath", filePath.toString())
        val file = File(filePath)
        val requestFile: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val multiPartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

        viewModel.uploadProfileImage(multiPartBody)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.profileImageLiveData.observe(this, Observer {
            when(it) {
                is NetworkResult.Success->{
                    Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Error ->{
                    Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    //
                }
            }
        })

        binding.btnImageUploadProfile.setOnClickListener(View.OnClickListener {

            uploadImage.launch("image/*")

        })

        /*binding.btnUpdate.setOnClickListener {
            *//*binding.btnNameUpdate.isEnabled = false
            val name = binding.edNameProfile.text.toString().trim()
            if (TextUtils.isEmpty(name)) {
                binding.edNameProfile.error = "Enter your name"
                binding.edNameProfile.requestFocus()
                binding.btnNameUpdate.isEnabled = true
            } else {
                callApi(getRestApis().nameChange(userInfo.data.id, name),
                    onApiSuccess = {
                        toast("Name changed successfully")
                        binding.btnNameUpdate.isEnabled = true
                        finish()
                    },
                    onApiError = {
                        toast(it)
                        binding.btnNameUpdate.isEnabled = true
                    },
                    onNetworkError = {
                        toast("Network error")
                        binding.btnNameUpdate.isEnabled = true
                    }
                )
            }*//*
        }*/
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}