package com.example.edupulse.ui.sittings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.edupulse.R
import com.example.edupulse.databinding.FragmentSittingsBinding
import com.example.edupulse.model.Users
import com.example.edupulse.ui.auth.RegistrationFragment.Companion.USER
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SittingsFragment : Fragment() {

    private var _binding: FragmentSittingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var myDataBase:DatabaseReference
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
        val user:FirebaseUser?=auth.currentUser
        val uid:String?=user?.uid
        myDataBase=Firebase.database.getReference(USER)
        if(uid!=null){
            myDataBase.child(uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value=snapshot.getValue(Users::class.java)
                    binding.tvUsername.text=value?.fullName
                    binding.tvEmail.text=value?.email
                    Glide.with(binding.imgProfile).load(value?.image).into(binding.imgProfile)

                }
                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }

    private fun initListeners() {
        binding.btnExit.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.authenticationFragment)
        }
        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}