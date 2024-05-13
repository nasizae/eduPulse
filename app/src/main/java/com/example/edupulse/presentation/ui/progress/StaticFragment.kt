package com.example.edupulse.presentation.ui.progress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.edupulse.R
import com.example.edupulse.databinding.FragmentStaticBinding
import com.example.edupulse.data.model.Users
import com.example.edupulse.presentation.ui.auth.RegistrationFragment.Companion.USER
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class StaticFragment : Fragment() {
    private lateinit var binding: FragmentStaticBinding
    private val auth=FirebaseAuth.getInstance()
    private lateinit var myDataBase:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentStaticBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProfileUser()
    }

    private fun initProfileUser() {
        myDataBase=Firebase.database.getReference(USER)
        val user:FirebaseUser?=auth.currentUser
        val uid:String?=user?.uid
        if(uid!=null){
            myDataBase.child(uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value=snapshot.getValue(Users::class.java)
                    if(value!=null){
                        binding.tvUsername.text=value.fullName
                        binding.tvUserEmail.text=value.email
                        Glide.with(binding.imgUser).load(value.image).into(binding.imgUser)
                    }
                    else{
                        Toast.makeText(requireContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }

    }

}