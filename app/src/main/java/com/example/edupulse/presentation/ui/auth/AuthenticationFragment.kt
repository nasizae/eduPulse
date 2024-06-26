package com.example.edupulse.presentation.ui.auth

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.edupulse.R
import com.example.edupulse.databinding.FragmentAuthenticationBinding
import com.example.edupulse.domain.usecase.LoginUseCase
import com.google.firebase.auth.FirebaseAuth

class AuthenticationFragment : Fragment() {
    private lateinit var binding: FragmentAuthenticationBinding
    private lateinit var loginUseCase: LoginUseCase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthenticationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSignInUsers()
    }

    private fun initSignInUsers() {
        loginUseCase=LoginUseCase(requireContext(),findNavController())
        binding.btnTransitionRegistration.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
        binding.btnSignIn.setOnClickListener {
            loginUseCase.login(binding.etEmail.text.toString(),
                binding.etPassword.text.toString())
        }

    }
}