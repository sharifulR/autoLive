package com.example.autolive.fragment

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.autolive.R
import com.example.autolive.WebViewActivity
import com.example.autolive.activity.EditProfileActivity
import com.example.autolive.api.UserApi
import com.example.autolive.databinding.FragmentProfileBinding
import com.example.autolive.utils.Constants.REQUEST_CODE_SELECT_IMAGE
import com.example.autolive.utils.NetworkResult
import com.example.autolive.utils.toast
import com.example.autolive.viewModel.ProfileImageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher


class ProfileFragment : Fragment() {


    lateinit var userApi: UserApi

    private var _binding:FragmentProfileBinding?=null
    private val binding get() = _binding!!
    private val profileImageViewModel by viewModels<ProfileImageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentProfileBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*bindObservers()*/

        //setMyInfo()

        binding.swipeToRefresh.setOnRefreshListener {
            //loadApis()
        }

        binding.btnPurchase.setOnClickListener {
            requireContext().toast("Under development")
        }

        binding.settings.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.shareApp.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Auto Live")
                var shareMessage = "\nYou Can Join With Us for Fun....... \n\n"
                shareMessage =
                    "${shareMessage}https://play.google.com/store/apps/details?id=${requireContext().packageName}".trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "Share Auto Live"))
            } catch (e: Exception) {
                requireContext().toast(e.localizedMessage!!)
            }
        }

        binding.helpFeedback.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "loveispowerlive@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
            intent.putExtra(Intent.EXTRA_TEXT, "Your Opinion : ")
            startActivity(intent)
        }

        binding.withdraw.setOnClickListener {
            requireContext().toast("Under development")
        }

        binding.game.setOnClickListener {
            requireContext().toast("Under development")
        }

        binding.privacyPolicy.setOnClickListener {
            /*val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("http://194.233.93.200/privacy-policy.html")
            startActivity(browserIntent)*/
            startActivity(
                Intent(requireContext(), WebViewActivity::class.java)
                    .putExtra("title", "Privacy Policy")
                    .putExtra("url", "http://194.233.93.200/privacy-policy.html")
            )
        }

        binding.termsAndConditions.setOnClickListener {
            /*val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("http://194.233.93.200/terms.html")
            startActivity(browserIntent)*/

            startActivity(
                Intent(requireContext(), WebViewActivity::class.java)
                    .putExtra("title", "Terms and Conditions")
                    .putExtra("url", "http://194.233.93.200/terms.html")
            )
        }

        binding.deleteAccount.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.layout_alert_dialog)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val no = dialog.findViewById<TextView>(R.id.no_dialog)
            val yes = dialog.findViewById<TextView>(R.id.yes_dialog)

            no.setOnClickListener { dialog.cancel() }

            /*yes.setOnClickListener {
                callApi(
                    getRestApis().deleteUser(),
                    onApiSuccess = {
                        if (it.isSuccess) {
                            requireContext().toast("Account successfully deleted")
                            dialog.dismiss()
                            Variable.accessToken = ""
                            Variable.editor.putString(
                                "token",
                                Variable.accessToken
                            )

                            Variable.editor.putString("phone", "") // = pref.edit();.p("token","");

                            Variable.editor.commit()
                            val intent = Intent(
                                requireContext(),
                                OTPPhoneNumberActivity::class.java
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            requireContext().toast(it.message)
                        }
                    },
                    onApiError = {
                        requireContext().toast(it)
                    },
                    onNetworkError = {
                        requireContext().toast("Network error")
                    }
                )
            }*/

            dialog.show()
        }
    }



   /* private fun loadApis() {
        loadMyProfile()
    }

    private fun loadMyProfile() {
        callApi(getRestApis().myProfile, onApiSuccess = {
            //hide progress
            binding.swipeToRefresh.isRefreshing = false

            if (it.isSuccess) {
                userInfo = it.profileData
                userProfileFollow = it.followers

                if (userInfo.getImage().isNullOrEmpty()) {
                    userInfo.setImage("")
                }

                setMyInfo()
            } else {
                requireContext().toast(it.message)
            }
        }, onApiError = {
            //hide progress
            binding.swipeToRefresh.isRefreshing = false
            requireContext().toast(it)
        }, onNetworkError = {
            //hide progress
            binding.swipeToRefresh.isRefreshing = false
            requireContext().toast("No Internet Connection")
        })
    }

    private fun setMyInfo() {

        if (!userInfo.getImage().isNullOrEmpty())
            binding.userImageProfile.loadImageFromUrl(BASE_URL + userInfo.getImage())

        binding.userNameProfile.text = userInfo.getName()
        binding.phoneNumberProfile.text = userInfo.getPhone().toString()
        binding.tvDiamond.text = userInfo.getPresentCoinBalance().toString()
        binding.followersProfile.text = userProfileFollow.size.toString()
    }*/

    private fun bindObservers() {
        profileImageViewModel.profileImageLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {


                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}