package com.example.autolive

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.autolive.databinding.FragmentRegistrationBinding
import com.example.autolive.models.RegisterRequest
import com.example.autolive.viewModel.AuthViewModel
import com.example.autolive.utils.NetworkResult
import com.example.autolive.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding?=null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel> ()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentRegistrationBinding.inflate(inflater, container, false)

        if (tokenManager.getToken() !=null){
            //findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
            val intent= Intent(this@RegistrationFragment.requireContext(),HomeActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupBtn.setOnClickListener(View.OnClickListener {
            val validationResult=validateUserInput()
            if (validationResult.first){
                authViewModel.signup(getUserRequest())
            }else{
                binding.Tv.text=validationResult.second
            }

        })

        binding.loginTV.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        })


        bindObservers()
    }
    private fun getUserRequest(): RegisterRequest{
        val name=binding.userName.text.toString()
        val mobile=binding.phoneNumber.text.toString()
        val password=binding.etPass.text.toString()
        val password_confirmation=binding.confPass.text.toString()
        return RegisterRequest(name,mobile,password,password_confirmation)
    }
    private fun validateUserInput(): Pair<Boolean, String> {

        val userRequest=getUserRequest()
        return authViewModel.validateCredentials(userRequest.name,userRequest.mobile,userRequest.password,userRequest.password_confirmation)
    }

    private fun bindObservers() {

        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible=false
            when (it){
                is NetworkResult.Success->{
                    tokenManager.saveToken(it.data!!.token)
                    //findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
                    val intent= Intent(this@RegistrationFragment.requireContext(),HomeActivity::class.java)
                    startActivity(intent)
                }
                is NetworkResult.Error->{
                    binding.Tv.text=it.message
                }
                is NetworkResult.Loading->{
                    binding.progressBar.isVisible=true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}