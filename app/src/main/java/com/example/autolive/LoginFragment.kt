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
import com.example.autolive.databinding.FragmentLoginBinding
import com.example.autolive.models.LoginRequest
import com.example.autolive.viewModel.AuthViewModel
import com.example.autolive.utils.NetworkResult
import com.example.autolive.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding?=null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel> ()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         _binding= FragmentLoginBinding.inflate(inflater, container, false)

        /*binding.loginBtn.setOnClickListener(View.OnClickListener {
            //findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            val intent= Intent(this@LoginFragment.requireContext(),HomeActivity::class.java)
            startActivity(intent)
        })*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.loginBtn.setOnClickListener(View.OnClickListener {
            val validationResult=validateUserInput()
            if (validationResult.first){
                authViewModel.login(getUserRequest())
            }else{
                binding.Tv.text=validationResult.second
            }

        })

        binding.signUpTV.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        })


        bindObservers()
    }
    private fun getUserRequest(): LoginRequest {
        val mobile=binding.etNumber.text.toString()
        val password=binding.etPass.text.toString()
        return LoginRequest(mobile,password)
    }
    private fun validateUserInput(): Pair<Boolean, String> {

        val userRequest=getUserRequest()
        return authViewModel.validateLoginCredentials(userRequest.mobile,userRequest.password)
    }

    private fun bindObservers() {

        authViewModel.userLoginResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible=false
            when (it){
                is NetworkResult.Success->{
                    tokenManager.saveToken(it.data!!.token)
                    //findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    val intent= Intent(this@LoginFragment.requireContext(),HomeActivity::class.java)
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