package com.example.edupulse.ui.auth

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
import com.google.firebase.auth.FirebaseAuth

class AuthenticationFragment : Fragment() {
    private lateinit var binding: FragmentAuthenticationBinding
    private lateinit var auth: FirebaseAuth
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
        binding.btnTransitionRegistration.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
        auth = FirebaseAuth.getInstance()
        binding.btnSignIn.setOnClickListener {
            if (!TextUtils.isEmpty(binding.etEmail.text.toString()) &&
                !TextUtils.isEmpty(binding.etPassword.text.toString())
            ) {
                auth.signInWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        findNavController().navigate(R.id.navigation_home)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "произошла ошибка проверьте правильность введённых данных",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            else{
                Toast.makeText(requireContext(), "пустое поле", Toast.LENGTH_SHORT).show()
            }
        }
    }
}