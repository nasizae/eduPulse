package com.example.edupulse.presentation.ui.sittings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.edupulse.R
import com.example.edupulse.databinding.AlertdialogExitAccountBinding
import com.example.edupulse.databinding.FragmentSittingsBinding
import com.example.edupulse.data.model.Users
import com.example.edupulse.domain.usecase.GetUserDataUseCase
import com.example.edupulse.presentation.ui.auth.RegistrationFragment.Companion.USER
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SittingsFragment : Fragment(),GetUserDataUseCase.CallBack{

    private var _binding: FragmentSittingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var alertDialog: AlertDialog
    private val getUserDataUseCase=GetUserDataUseCase()
    private val auth=FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSittingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        getProfile()
    }

    private fun getProfile() {
       getUserDataUseCase.getUser(this)
    }
    private fun initListeners() {
        binding.btnExit.setOnClickListener {
            initAlertDialogExit()

        }
        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }
    }

    private fun initAlertDialogExit() {
        val alertDialogBuilder=AlertDialog.Builder(requireContext(),R.style.CustomAlertDialogStyle)
        val alertDialogBinding=AlertdialogExitAccountBinding.inflate(layoutInflater)
        alertDialogBuilder.setView(alertDialogBinding.root)
        alertDialog=alertDialogBuilder.create()
        alertDialogBinding.btnYes.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.authenticationFragment)
            alertDialog.dismiss()
        }
        alertDialogBinding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onUserReceived(users: Users) {
        binding.tvEmail.text=users.email
        binding.tvUsername.text=users.fullName
        Glide.with(binding.imgProfile).load(users.image).into(binding.imgProfile)
    }

    override fun onError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}